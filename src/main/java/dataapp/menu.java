package dataapp;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dataapp.dto.blooddatarequest;
import dataapp.dto.bodydatarequest;
import dataapp.entity.userdata;
import dataapp.entity.userdatablood;
import dataapp.entity.userinformation;

@RestController
@RequestMapping(value = "/menu", produces = MediaType.APPLICATION_JSON_VALUE)
public class menu {

	@PostMapping("bodydata")
	public ResponseEntity<String> bodydata(@RequestParam("jwt") final String jwt) {

		try {

			String userName = util.varifyjwt(jwt, "username");

			EntityManagerFactory emf = Persistence.createEntityManagerFactory("dataapp");
			EntityManager em = emf.createEntityManager();

			userinformation user = em.createNamedQuery("userinformation.findbyusername", userinformation.class)
					.setParameter(1, userName).getSingleResult();

			List<userdata> userData = em.createNamedQuery("userdata.finduserid_desc", userdata.class)
					.setParameter(1, user).getResultList();

			if (!Objects.isNull(userData)) {
				ObjectMapper mapper = new ObjectMapper();
				String res = mapper.writeValueAsString(userData);

				return new ResponseEntity<String>(res, HttpStatus.OK);

			} else {
				return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
			}

		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

	@PostMapping("blooddata")
	public ResponseEntity<String> blooddata(@RequestParam("jwt") final String jwt) {

		try {

			String userName = util.varifyjwt(jwt, "username");

			EntityManagerFactory emf = Persistence.createEntityManagerFactory("dataapp");
			EntityManager em = emf.createEntityManager();

			userinformation user = em.createNamedQuery("userinformation.findbyusername", userinformation.class)
					.setParameter(1, userName).getSingleResult();

			List<userdatablood> userData = em.createNamedQuery("userdatablood.finduserid", userdatablood.class)
					.setParameter(1, user).getResultList();

			if (!Objects.isNull(userData)) {
				ObjectMapper mapper = new ObjectMapper();
				String res = mapper.writeValueAsString(userData);

				return new ResponseEntity<String>(res, HttpStatus.OK);

			} else {
				return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
			}

		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

	@DeleteMapping("trashdata")
	public ResponseEntity<String> trash(@RequestParam("jwt") final String jwt, @RequestParam("id") final int id)
			throws JsonProcessingException {

		try {

			EntityTransaction tx = null;
			String userName = util.varifyjwt(jwt, "username");

			EntityManagerFactory emf = Persistence.createEntityManagerFactory("dataapp");
			EntityManager em = emf.createEntityManager();

			userinformation user = em.createNamedQuery("userinformation.findbyusername", userinformation.class)
					.setParameter(1, userName).getSingleResult();

			tx = em.getTransaction();
			tx.begin();

			userdata delData = em.createNamedQuery("userdata.selectdata", userdata.class).setParameter(1, user)
					.setParameter(2, id).getSingleResult();

			em.createNamedQuery("userdata.deletedata", userdata.class).setParameter(1, user).setParameter(2, id)
					.executeUpdate();

			tx.commit();

			if (!Objects.isNull(delData)) {
				ObjectMapper mapper = new ObjectMapper();
				String res = mapper.writeValueAsString(delData);

				return new ResponseEntity<String>(res, HttpStatus.OK);

			} else {
				return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
			}

		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

	@PostMapping("insertdata")
	public ResponseEntity<String> insertdata(@Validated bodydatarequest bodydata, BindingResult result)
			throws JsonProcessingException {

		if (result.hasErrors()) {
			Map<String, String> valueMap = util.validdheck(result);

			ObjectMapper mapper = new ObjectMapper();
			String res = mapper.writeValueAsString(valueMap);

			return new ResponseEntity<String>(res, HttpStatus.BAD_REQUEST);

		}

		try {

			EntityTransaction tx = null;
			String userName = util.varifyjwt(bodydata.getJwt(), "username");

			EntityManagerFactory emf = Persistence.createEntityManagerFactory("dataapp");
			EntityManager em = emf.createEntityManager();

			userinformation user = em.createNamedQuery("userinformation.findbyusername", userinformation.class)
					.setParameter(1, userName).getSingleResult();

			tx = em.getTransaction();
			tx.begin();

			userdata userdata = new userdata();
			userdata.setUserinformation(user);
			userdata.setHeight(bodydata.getHeight());
			userdata.setWeight(bodydata.getWeight());

			long millis = System.currentTimeMillis();
			userdata.setCreateTime(new Timestamp(millis));
			userdata.setModifiedTime(new Timestamp(millis));

			em.persist(userdata);

			tx.commit();

			List<userdata> userData = em.createNamedQuery("userdata.finduserid_desc", userdata.class)
					.setParameter(1, user).getResultList();

			ObjectMapper mapper = new ObjectMapper();
			String res = mapper.writeValueAsString(userData);

			return new ResponseEntity<String>(res, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

	@PostMapping("updatedata")
	public ResponseEntity<String> updatedata(@Validated blooddatarequest blooddata, BindingResult result)
			throws JsonProcessingException {

		if (result.hasErrors()) {
			Map<String, String> valueMap = util.validdheck(result);

			ObjectMapper mapper = new ObjectMapper();
			String res = mapper.writeValueAsString(valueMap);

			return new ResponseEntity<String>(res, HttpStatus.BAD_REQUEST);

		}

		try {

			String userName = util.varifyjwt(blooddata.getJwt(), "username");

			EntityManagerFactory emf = Persistence.createEntityManagerFactory("dataapp");
			EntityManager em = emf.createEntityManager();

			userinformation user = em.createNamedQuery("userinformation.findbyusername", userinformation.class)
					.setParameter(1, userName).getSingleResult();

			EntityTransaction tx = em.getTransaction();
			tx.begin();

			String QueryName = "userdatablood.update_" + blooddata.getBloodname();

			em.createNamedQuery(QueryName, userdatablood.class).setParameter(1, blooddata.getNewvalue())
					.setParameter(2, user).executeUpdate();

			tx.commit();

			return new ResponseEntity<String>(HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

}
