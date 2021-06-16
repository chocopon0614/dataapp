package DataApp;

import java.sql.Timestamp;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import DataApp.entity.Userdatablood;
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

		Userinformation UserObj = em.createNamedQuery("Userinformation.findbyusername", Userinformation.class)
				.setParameter(1, UserName).getSingleResult();

		if (!(UserObj == null)) {
			String DbPass = UserObj.getPassword();
			if (hash_password.equals(DbPass)) {

				String jwttoken = jwtutil.createJWT(UserName, DbPass);
				String res = "{\"JWT\" : \"" + jwttoken + "\" }";

				return new ResponseEntity<String>(res, HttpStatus.OK);

			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

			}

		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PostMapping("register")
	public ResponseEntity<String> register(@RequestParam("username") final String username,
			@RequestParam("password") final String password) throws Exception {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("DataApp");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = null;

		EntityManagerFactory emf2 = Persistence.createEntityManagerFactory("DataApp");
		EntityManager em2 = emf2.createEntityManager();
		EntityTransaction tx2 = null;

		try {
			String hash_password = hashutil.getSHA256(password);

			tx = em.getTransaction();
			tx.begin();

			Userinformation userinfo = new Userinformation();
			userinfo.setUsername(username);
			userinfo.setPassword(hash_password);
			userinfo.setCreateTime(new Timestamp(System.currentTimeMillis()));
			userinfo.setModifiedTime(new Timestamp(System.currentTimeMillis()));

			em.persist(userinfo);

			tx.commit();

			tx2 = em2.getTransaction();
			tx2.begin();

			Userinformation UserObj = em2.createNamedQuery("Userinformation.findbyusername", Userinformation.class)
					.setParameter(1, username).getSingleResult();

			Userdatablood userblood = new Userdatablood();
			userblood.setUserinformation(UserObj);
			userblood.setFpg(0);
			userblood.setGtp(0);
			userblood.setHdl(0);
			userblood.setLdl(0);
			userblood.setTg(0);

			em2.persist(userblood);

			tx2.commit();

			return ResponseEntity.ok().build();

		} catch (RuntimeException e) {
			if (tx != null && tx.isActive())
				tx.rollback();

			if (tx2 != null && tx2.isActive())
				tx2.rollback();

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

		} finally {
			em.close();
		}

	}

	@DeleteMapping("userdelete")
	public ResponseEntity<String> userDelete(@RequestParam("jwt") final String jwt) throws Exception {

		String UserName = jwtutil.varifyJWT(jwt, "username");

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("DataApp");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = null;

		try {

			tx = em.getTransaction();
			tx.begin();

			em.createNamedQuery("Userinformation.deletebyusername", Userinformation.class).setParameter(1, UserName)
					.executeUpdate();

			tx.commit();

			return ResponseEntity.ok().build();

		} catch (RuntimeException e) {
			if (tx != null && tx.isActive())
				tx.rollback();

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

		} finally {
			em.close();
		}

	}

	@PostMapping("authorization")
	public ResponseEntity<String> authorization(@RequestParam("jwt") final String jwt) {

		try {

			String username = jwtutil.varifyJWT(jwt, "username");
			String password = jwtutil.varifyJWT(jwt, "password");

			String res = "{\"username\" : \"" + username + "\" , \"password\" : \"" + password + "\"}";
			return new ResponseEntity<String>(res, HttpStatus.OK);

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

}
