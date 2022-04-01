package dataapp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
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
	private UserInformationDao daoUser;

	@Autowired
	private UserDataDao daoData;

	@Autowired
	private UserDatabloodDao daoBlood;

	@GetMapping("bardata")
	public ResponseEntity<String> userInfo(@RequestHeader("Authorization") String authorization) {

		try {
			String token = authorization.split(" ")[1];
			DecodedJWT decodedToken = new JWT().decodeJwt(token);
			String userName = decodedToken.getClaim("username").asString();

			UserInformation user = daoUser.findByUsername(userName);
			List<UserData> userData = daoData.findByUseridSelected(user);

			ObjectMapper mapper = new ObjectMapper();
			String res = mapper.writeValueAsString(userData);
			return new ResponseEntity<String>(res, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("chartdata")
	public ResponseEntity<String> chartData(@RequestHeader("Authorization") String authorization) {

		try {
			String token = authorization.split(" ")[1];
			DecodedJWT decodedToken = new JWT().decodeJwt(token);
			String userName = decodedToken.getClaim("username").asString();

			UserInformation user = daoUser.findByUsername(userName);
			UserDatablood userData = daoBlood.findByUserid(user);

			ObjectMapper mapper = new ObjectMapper();
			String res = mapper.writeValueAsString(userData);
			return new ResponseEntity<String>(res, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
