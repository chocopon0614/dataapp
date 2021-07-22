package dataapp;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.fasterxml.jackson.databind.ObjectMapper;

import dataapp.dao.UserDataDao;
import dataapp.dao.UserDatabloodDao;
import dataapp.dao.UserInformationDao;
import dataapp.dto.BloodDataRequest;
import dataapp.dto.BodyDataRequest;
import dataapp.entity.UserData;
import dataapp.entity.UserDatablood;
import dataapp.entity.UserInformation;

@RestController
@RequestMapping(value = "/menu", produces = MediaType.APPLICATION_JSON_VALUE)
public class Menu {
	@Autowired
	private Util util;

	@Autowired
	private UserInformationDao daoUser;

	@Autowired
	private UserDataDao daoData;

	@Autowired
	private UserDatabloodDao daoBlood;

	@PostMapping("bodydata")
	public ResponseEntity<String> bodyData(@RequestParam("jwt") final String jwt) {

		try {

			String userName = util.varifyJwt(jwt, "userName");

			UserInformation user = daoUser.findByUsername(userName);
			List<UserData> userData = daoData.findByUserid(user);

			userData.sort(Comparator.comparing(UserData::getId).reversed());

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
	public ResponseEntity<String> bloodData(@RequestParam("jwt") final String jwt) {

		try {

			String userName = util.varifyJwt(jwt, "userName");

			UserInformation user = daoUser.findByUsername(userName);
			UserDatablood userData = daoBlood.findByUserid(user);

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

	@DeleteMapping("deletedata")
	public ResponseEntity<String> deleteData(@RequestParam("jwt") final String jwt, @RequestParam("id") final int id) {

		try {

			String userName = util.varifyJwt(jwt, "userName");

			UserData delData = daoData.find(id);
			if (!userName.equals(delData.getUserinformation().getUsername()))
				return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);

			daoData.remove(delData);

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
	public ResponseEntity<String> insertData(@Validated BodyDataRequest bodyData, BindingResult result) {

		try {

			if (result.hasErrors()) {
				Map<String, String> valueMap = util.validCheck(result);

				ObjectMapper mapper = new ObjectMapper();
				String res = mapper.writeValueAsString(valueMap);

				return new ResponseEntity<String>(res, HttpStatus.BAD_REQUEST);

			}

			String userName = util.varifyJwt(bodyData.getJwt(), "userName");
			UserInformation user = daoUser.findByUsername(userName);

			UserData userData = new UserData();
			userData.setUserinformation(user);
			userData.setHeight(bodyData.getHeight());
			userData.setWeight(bodyData.getWeight());

			daoData.persist(userData);

			return new ResponseEntity<String>(HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

	@PostMapping("updatedata")
	public ResponseEntity<String> updateData(@Validated BloodDataRequest bloodData, BindingResult result) {

		try {

			if (result.hasErrors()) {
				Map<String, String> valueMap = util.validCheck(result);

				ObjectMapper mapper = new ObjectMapper();
				String res = mapper.writeValueAsString(valueMap);

				return new ResponseEntity<String>(res, HttpStatus.BAD_REQUEST);

			}

			String userName = util.varifyJwt(bloodData.getJwt(), "userName");
			UserInformation user = daoUser.findByUsername(userName);

			daoBlood.update(bloodData.getBloodname(), bloodData.getNewvalue(), daoBlood.findByUserid(user));

			return new ResponseEntity<String>(HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

}
