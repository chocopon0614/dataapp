package dataapp;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import dataapp.dao.UserDataDao;
import dataapp.dao.UserDatabloodDao;
import dataapp.dao.UserInformationDao;
import dataapp.entity.UserData;
import dataapp.entity.UserDatablood;
import dataapp.entity.UserInformation;

@CrossOrigin
@RestController
@RequestMapping(value = "/open", produces = MediaType.APPLICATION_JSON_VALUE)
public class Open {
	@Autowired
	private Util util;

	@Autowired
	private UserInformationDao daoUser;

	@Autowired
	private UserDataDao daoData;

	@Autowired
	private UserDatabloodDao daoBlood;

	@GetMapping("bardata")
	public ResponseEntity<String> userInfo(@RequestHeader("Token") String token) {

		try {

			String tokenCheck = util.tokenCheck(token);
			ObjectMapper mapper = new ObjectMapper();
			JsonNode root = mapper.readTree(tokenCheck);

			if (!root.get("active").asBoolean())
				return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);

			String userName = root.get("userName").asText();
			UserInformation user = daoUser.findByUsername(userName);

			List<UserData> userData = daoData.findByUseridSelected(user);

			if (!Objects.isNull(userData)) {
				String res = mapper.writeValueAsString(userData);
				return new ResponseEntity<String>(res, HttpStatus.OK);

			} else {
				return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);

			}

		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("chartdata")
	public ResponseEntity<String> chartData(@RequestHeader("Token") String token) {

		try {

			String tokenCheck = util.tokenCheck(token);
			ObjectMapper mapper = new ObjectMapper();
			JsonNode root = mapper.readTree(tokenCheck);

			if (!root.get("active").asBoolean())
				return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);

			String userName = root.get("userName").asText();
			UserInformation user = daoUser.findByUsername(userName);

			UserDatablood userData = daoBlood.findByUserid(user);

			if (!Objects.isNull(userData)) {
				String res = mapper.writeValueAsString(userData);
				return new ResponseEntity<String>(res, HttpStatus.OK);

			} else {
				return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);

			}

		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
