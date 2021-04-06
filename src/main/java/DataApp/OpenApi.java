package DataApp;

import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import DataApp.entity.Userdata;
import DataApp.entity.Userdatablood;
import DataApp.entity.Userinformation;

@RestController
@RequestMapping(value = "/open", produces = MediaType.APPLICATION_JSON_VALUE)
public class OpenApi {

	@GetMapping("userinfo")
	public ResponseEntity<String> userinfo(@RequestHeader("Authorization") String authorization)
			throws JsonProcessingException {

		String token = authorization.split(" ")[1];
		DecodedJWT decodedToken = new JWT().decodeJwt(token);
		String UserName = decodedToken.getClaim("username").asString();

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("DataApp");
		EntityManager em = emf.createEntityManager();

		Userinformation userobj = em.createNamedQuery("Userinformation.findbyusername", Userinformation.class)
				.setParameter(1, UserName).getSingleResult();

		List<Userdata> userdata_list = em.createNamedQuery("Userdata.findUserid_selected", Userdata.class)
				.setParameter(1, userobj).getResultList();

		if (!Objects.isNull(userdata_list)) {
			ObjectMapper mapper = new ObjectMapper();
			String resjson = mapper.writeValueAsString(userdata_list);

			return new ResponseEntity<String>(resjson, HttpStatus.OK);

		} else {
			return ResponseEntity.badRequest().build();

		}

	}

	@GetMapping("chartdata")
	public ResponseEntity<String> chartdata(@RequestHeader("Authorization") String authorization)
			throws JsonProcessingException {
		String token = authorization.split(" ")[1];
		DecodedJWT decodedToken = new JWT().decodeJwt(token);
		String UserName = decodedToken.getClaim("username").asString();

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("DataApp");
		EntityManager em = emf.createEntityManager();

		Userinformation userobj = em.createNamedQuery("Userinformation.findbyusername", Userinformation.class)
				.setParameter(1, UserName).getSingleResult();

		List<Userdatablood> userdata_list = em.createNamedQuery("Userdatablood.findUserid", Userdatablood.class)
				.setParameter(1, userobj).getResultList();

		if (!Objects.isNull(userdata_list)) {
			ObjectMapper mapper = new ObjectMapper();
			String resjson = mapper.writeValueAsString(userdata_list);

			return new ResponseEntity<String>(resjson, HttpStatus.OK);

		} else {
			return ResponseEntity.badRequest().build();

		}
	}

}
