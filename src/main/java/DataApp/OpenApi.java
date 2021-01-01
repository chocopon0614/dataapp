package DataApp;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import DataApp.entity.Userdata;
import DataApp.entity.Userdatablood;
import DataApp.entity.Userinformation;
import DataApp.util.jwtutil;

@RestController
@RequestMapping(value = "/open", produces = MediaType.APPLICATION_JSON_VALUE)
public class OpenApi {

	@GetMapping("chartdata")
    public ResponseEntity<String> chartdata(@RequestHeader("resource-owner") String resourceowner) throws JsonProcessingException {
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("DataApp");
		EntityManager em = emf.createEntityManager();
		
		Userinformation userobj = em.createNamedQuery("Userinformation.findbyusername",Userinformation.class)
				.setParameter(1, resourceowner)
				.getSingleResult();

		List<Userdatablood> userdata_list = em.createNamedQuery("Userdatablood.findUserid", Userdatablood.class)
				.setParameter(1, userobj)
				.getResultList();
		
		if (!Objects.isNull(userdata_list)) {
			ObjectMapper mapper = new ObjectMapper();
			String resjson = mapper.writeValueAsString(userdata_list);

			return new ResponseEntity<String>(resjson, HttpStatus.OK);
			    
			} else {
			    return ResponseEntity.badRequest().build();
			
			}
		}
	
	@GetMapping("userinfo")
    public ResponseEntity<String> userinfo(@RequestHeader("resource-owner") String resourceowner) throws JsonProcessingException {
	
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("DataApp");
		EntityManager em = emf.createEntityManager();
		
		Userinformation userobj = em.createNamedQuery("Userinformation.findbyusername",Userinformation.class)
				.setParameter(1, resourceowner)
				.getSingleResult();

		List<Userdata> userdata_list = em.createNamedQuery("Userdata.findUserid_selected", Userdata.class)
				.setParameter(1, userobj)
				.getResultList();
		
		if (!Objects.isNull(userdata_list)) {
			ObjectMapper mapper = new ObjectMapper();
			String resjson = mapper.writeValueAsString(userdata_list);

			return new ResponseEntity<String>(resjson, HttpStatus.OK);
			    
			} else {
			    return ResponseEntity.badRequest().build();
			
			}
		}


	@GetMapping("verification")
    public ResponseEntity<String> verification(@RequestHeader("Authorization") String authorization) {
		
		String ConfirmationCode = authorization.split(" ")[1];

		Charset charset = StandardCharsets.UTF_8;

		byte[] dec = Base64.getDecoder().decode(ConfirmationCode);
		String decstr = new String(dec,charset);

		String UserName = decstr.split(":")[0];
		String PassWord = decstr.split(":")[1];

		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("DataApp");
		EntityManager em = emf.createEntityManager();
		
		Userinformation UserObj = em.createNamedQuery("Userinformation.findbyusername",Userinformation.class)
				.setParameter(1, UserName)
				.getSingleResult();

		if (!Objects.isNull(UserObj)) {
			String DbPass = UserObj.getPassword();
			if (PassWord.equals(DbPass)) {

		      return ResponseEntity.ok().build();
			    
			} else {
			    return ResponseEntity.badRequest().build();
			}

		}else {

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

		}
    }
	
	@PostMapping("authorization")
    public ResponseEntity<String> authorization(@RequestParam("jwt") final String jwt ) throws Exception {

	try{
		
		String username = jwtutil.varifyJWT(jwt, "username");
		String password = jwtutil.varifyJWT(jwt, "password");
		
		String res = "{\"username\" : \"" + username + "\" , \"password\" : \"" + password + "\"}";

		return new ResponseEntity<String>(res, HttpStatus.OK);

    }catch(Exception e) {
    	throw e;

	  }
    }

}
