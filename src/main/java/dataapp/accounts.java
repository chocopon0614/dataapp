package dataapp;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
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

import dataapp.entity.userdatablood;
import dataapp.entity.userinformation;

@RestController
@RequestMapping(value = "/accounts", produces = MediaType.APPLICATION_JSON_VALUE)
public class accounts {

	@PostMapping("login")
	public ResponseEntity<String> userLogin(@RequestParam("username") final String userName,
			@RequestParam("password") final String passWord) {

		String hashedPassword = null;

		try {
			hashedPassword = util.getsha256(passWord);
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		String dbPassword = util.getdbpassword(userName);

		if (hashedPassword.equals(dbPassword)) {

			String jwttoken = util.createjwt(userName, dbPassword);
			String res = "{\"jwt\" : \"" + jwttoken + "\" }";

			return new ResponseEntity<String>(res, HttpStatus.OK);

		} else {
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);

		}

	}

	@PostMapping("register")
	public ResponseEntity<String> register(@RequestParam("username") final String userName,
			@RequestParam("password") final String passWord) {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("dataapp");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = null;

		EntityManagerFactory emf2 = Persistence.createEntityManagerFactory("dataapp");
		EntityManager em2 = emf2.createEntityManager();
		EntityTransaction tx2 = null;

		try {
			String hashedPassword = util.getsha256(passWord);

			tx = em.getTransaction();
			tx.begin();

			userinformation user = new userinformation();
			user.setUsername(userName);
			user.setPassword(hashedPassword);
			user.setCreateTime(new Timestamp(System.currentTimeMillis()));
			user.setModifiedTime(new Timestamp(System.currentTimeMillis()));

			em.persist(user);

			tx.commit();

			tx2 = em2.getTransaction();
			tx2.begin();

			userinformation registerdUser = em2
					.createNamedQuery("userinformation.findbyusername", userinformation.class).setParameter(1, userName)
					.getSingleResult();

			userdatablood bloodInitialData = new userdatablood();
			bloodInitialData.setUserinformation(registerdUser);
			bloodInitialData.setFpg(0);
			bloodInitialData.setGtp(0);
			bloodInitialData.setHdl(0);
			bloodInitialData.setLdl(0);
			bloodInitialData.setTg(0);

			em2.persist(bloodInitialData);

			tx2.commit();

			return new ResponseEntity<String>(HttpStatus.OK);

		} catch (RuntimeException | NoSuchAlgorithmException | UnsupportedEncodingException e) {
			if (tx != null && tx.isActive())
				tx.rollback();

			if (tx2 != null && tx2.isActive())
				tx2.rollback();

			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);

		} finally {
			em.close();
		}

	}

	@DeleteMapping("delete")
	public ResponseEntity<String> userDelete(@RequestParam("jwt") final String jwt) {

		String UserName = util.varifyjwt(jwt, "username");

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("dataAap");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = null;

		try {

			tx = em.getTransaction();
			tx.begin();

			em.createNamedQuery("userinformation.deletebyusername", userinformation.class).setParameter(1, UserName)
					.executeUpdate();

			tx.commit();

			return new ResponseEntity<String>(HttpStatus.OK);

		} catch (RuntimeException e) {
			if (tx != null && tx.isActive())
				tx.rollback();

			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);

		} finally {
			em.close();
		}

	}

}
