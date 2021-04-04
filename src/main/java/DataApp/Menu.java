package DataApp;

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

import DataApp.dto.BloodDataRequest;
import DataApp.dto.BodyDataRequest;
import DataApp.entity.Userdata;
import DataApp.entity.Userdatablood;
import DataApp.entity.Userinformation;
import DataApp.util.checkutil;
import DataApp.util.jwtutil;

@RestController
@RequestMapping(value = "/menu", produces = MediaType.APPLICATION_JSON_VALUE)
public class Menu {

	@PostMapping("bodydata")
	public ResponseEntity<String> tabledata(@RequestParam("jwt") final String jwt) throws JsonProcessingException {

		try {

			String UserName = jwtutil.varifyJWT(jwt, "username");

			EntityManagerFactory emf = Persistence.createEntityManagerFactory("DataApp");
			EntityManager em = emf.createEntityManager();

			Userinformation UserObj = em.createNamedQuery("Userinformation.findbyusername", Userinformation.class)
					.setParameter(1, UserName).getSingleResult();

			List<Userdata> UserDataList = em.createNamedQuery("Userdata.findUserid_desc", Userdata.class)
					.setParameter(1, UserObj).getResultList();

			if (!Objects.isNull(UserDataList)) {
				ObjectMapper mapper = new ObjectMapper();
				String ResJson = mapper.writeValueAsString(UserDataList);

				return new ResponseEntity<String>(ResJson, HttpStatus.OK);

			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

		}

	}

	@DeleteMapping("trash")
	public ResponseEntity<String> datatrash(@RequestParam("jwt") final String jwt, @RequestParam("id") final int id)
			throws JsonProcessingException {

		try {

			EntityTransaction tx = null;
			String UserName = jwtutil.varifyJWT(jwt, "username");

			EntityManagerFactory emf = Persistence.createEntityManagerFactory("DataApp");
			EntityManager em = emf.createEntityManager();

			Userinformation UserObj = em.createNamedQuery("Userinformation.findbyusername", Userinformation.class)
					.setParameter(1, UserName).getSingleResult();

			tx = em.getTransaction();
			tx.begin();

			Userdata del_data = em.createNamedQuery("Userdata.selectData", Userdata.class).setParameter(1, UserObj)
					.setParameter(2, id).getSingleResult();

			em.createNamedQuery("Userdata.deleteData", Userdata.class).setParameter(1, UserObj).setParameter(2, id)
					.executeUpdate();

			tx.commit();

			if (!Objects.isNull(del_data)) {
				ObjectMapper mapper = new ObjectMapper();
				String ResJson = mapper.writeValueAsString(del_data);

				return new ResponseEntity<String>(ResJson, HttpStatus.OK);

			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

		}

	}

	@PostMapping("insertdata")
	public ResponseEntity<String> insertdata(@Validated BodyDataRequest bodydata, BindingResult result)
			throws JsonProcessingException {

		if (result.hasErrors()) {
			Map<String, String> valueMap = checkutil.validCheck(result);

			ObjectMapper mapper = new ObjectMapper();
			String ResJson = mapper.writeValueAsString(valueMap);

			return new ResponseEntity<String>(ResJson, HttpStatus.BAD_REQUEST);

		}

		try {

			EntityTransaction tx = null;
			String UserName = jwtutil.varifyJWT(bodydata.getJwt(), "username");

			EntityManagerFactory emf = Persistence.createEntityManagerFactory("DataApp");
			EntityManager em = emf.createEntityManager();

			Userinformation UserObj = em.createNamedQuery("Userinformation.findbyusername", Userinformation.class)
					.setParameter(1, UserName).getSingleResult();

			tx = em.getTransaction();
			tx.begin();

			Userdata userdata = new Userdata();
			userdata.setUserinformation(UserObj);
			userdata.setHeight(bodydata.getHeight());
			userdata.setWeight(bodydata.getWeight());

			long millis = System.currentTimeMillis();
			userdata.setCreateTime(new Timestamp(millis));
			userdata.setModifiedTime(new Timestamp(millis));

			em.persist(userdata);

			tx.commit();

			List<Userdata> UserDataList = em.createNamedQuery("Userdata.findUserid_desc", Userdata.class)
					.setParameter(1, UserObj).getResultList();

			ObjectMapper mapper = new ObjectMapper();
			String ResJson = mapper.writeValueAsString(UserDataList);

			return new ResponseEntity<String>(ResJson, HttpStatus.OK);

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

		}

	}

	@PostMapping("blooddata")
	public ResponseEntity<String> blooddata(@RequestParam("jwt") final String jwt) throws JsonProcessingException {

		try {

			String UserName = jwtutil.varifyJWT(jwt, "username");

			EntityManagerFactory emf = Persistence.createEntityManagerFactory("DataApp");
			EntityManager em = emf.createEntityManager();

			Userinformation UserObj = em.createNamedQuery("Userinformation.findbyusername", Userinformation.class)
					.setParameter(1, UserName).getSingleResult();

			List<Userdatablood> UserDataList = em.createNamedQuery("Userdatablood.findUserid", Userdatablood.class)
					.setParameter(1, UserObj).getResultList();

			if (!Objects.isNull(UserDataList)) {
				ObjectMapper mapper = new ObjectMapper();
				String ResJson = mapper.writeValueAsString(UserDataList);

				return new ResponseEntity<String>(ResJson, HttpStatus.OK);

			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

		}

	}

	@PostMapping("updatedata")
	public ResponseEntity<String> updatedata(@Validated BloodDataRequest blooddata, BindingResult result)
			throws JsonProcessingException {

		if (result.hasErrors()) {
			Map<String, String> valueMap = checkutil.validCheck(result);

			ObjectMapper mapper = new ObjectMapper();
			String ResJson = mapper.writeValueAsString(valueMap);

			return new ResponseEntity<String>(ResJson, HttpStatus.BAD_REQUEST);

		}

		try {

			String UserName = jwtutil.varifyJWT(blooddata.getJwt(), "username");

			EntityManagerFactory emf = Persistence.createEntityManagerFactory("DataApp");
			EntityManager em = emf.createEntityManager();

			Userinformation UserObj = em.createNamedQuery("Userinformation.findbyusername", Userinformation.class)
					.setParameter(1, UserName).getSingleResult();

			EntityTransaction tx = em.getTransaction();
			tx.begin();

			String QueryName = "Userdatablood.update_" + blooddata.getBloodname();

			em.createNamedQuery(QueryName, Userdatablood.class).setParameter(1, blooddata.getNewvalue())
					.setParameter(2, UserObj).executeUpdate();

			tx.commit();

			return ResponseEntity.ok().build();

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

		}

	}

}
