package DataApp;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import DataApp.entity.Userinformation;
import DataApp.util.hashutil;
import DataApp.util.jwtutil;

@RestController
@RequestMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
public class Login {
	
	@PostMapping("userlogin")
	public ResponseEntity<String> userLogin(@RequestParam("username") final String UserName, 
			@RequestParam("password") final String PassWord) {
		
		String hash_password = hashutil.getSHA256(PassWord);


		EntityManagerFactory emf = Persistence.createEntityManagerFactory("DataApp");
		EntityManager em = emf.createEntityManager();
		
		List<Userinformation> UserObj = em.createNamedQuery("Userinformation.findbyusername",Userinformation.class)
				.setParameter(1, UserName)
				.getResultList();
		
		if (!(UserObj == null || UserObj.size() == 0)) {
			String DbPass = UserObj.get(0).getPassword();
			if (hash_password.equals(DbPass)) {
				
				String jwttoken = jwtutil.createJWT(UserName, DbPass);
				String res = "{\"JWT\" : \"" + jwttoken + "\" }";
				
				return new ResponseEntity<String>(res, HttpStatus.OK);
				
			} else {
			    return ResponseEntity.badRequest().build();

			}
				
		}else {
		    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	  }
	
	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestParam("username") final String username,
			@RequestParam("password") final String password) throws Exception {
		
		String hash_password = hashutil.getSHA256(password);

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("DataApp");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = null;
		
		try {
	      tx = em.getTransaction();
	      tx.begin();

	      Userinformation userinfo = new Userinformation();
	      userinfo.setUsername(username);
	      userinfo.setPassword(hash_password);
	      userinfo.setCreateTime(new Timestamp(System.currentTimeMillis()));
	      userinfo.setModifiedTime(new Timestamp(System.currentTimeMillis()));

	      em.persist(userinfo);
	    
	      tx.commit();

		  return ResponseEntity.ok().build();

		}catch (RuntimeException e) {
			if ( tx != null && tx.isActive() ) tx.rollback();

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			
		} finally {
			em.close();
		}
		
	}
	

 }
