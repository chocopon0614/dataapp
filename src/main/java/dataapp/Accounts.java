package dataapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dataapp.dao.UserDatabloodDao;
import dataapp.dao.UserInformationDao;
import dataapp.entity.UserDatablood;
import dataapp.entity.UserInformation;

@RestController
@RequestMapping(value = "/accounts", produces = MediaType.APPLICATION_JSON_VALUE)
public class Accounts {
	@Autowired
	private Util util;

	@Autowired
	private UserInformationDao daoUser;

	@Autowired
	private UserDatabloodDao daoBlood;

	@PostMapping("login")
	public ResponseEntity<String> login(@RequestParam("userName") final String userName,
			@RequestParam("passWord") final String passWord) {

		String hashedPassword = null;

		try {
			hashedPassword = util.getSha256(passWord);

			UserInformation user = daoUser.findByUsername(userName);
			String dbPassword = user.getPassword();

			if (!(user == null) && hashedPassword.equals(dbPassword)) {

				String jwt = util.createJwt(userName, dbPassword);
				String res = "{\"jwt\" : \"" + jwt + "\" }";

				return new ResponseEntity<String>(res, HttpStatus.OK);

			} else {
				return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);

			}

		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PostMapping("register")
	public ResponseEntity<String> register(@RequestParam("userName") final String userName,
			@RequestParam("passWord") final String passWord) {

		try {
			String hashedPassword = util.getSha256(passWord);

			UserInformation user = new UserInformation();
			user.setUsername(userName);
			user.setPassword(hashedPassword);
			daoUser.persist(user);

			UserDatablood data = new UserDatablood();
			data.setUserinformation(daoUser.findByUsername(userName));
			daoBlood.persist(data);

			util.userPool(userName, hashedPassword);

			return new ResponseEntity<String>(HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@DeleteMapping("delete")
	public ResponseEntity<String> delete(@RequestParam("jwt") final String jwt) {

		try {

			String userName = util.varifyJwt(jwt, "userName");

			UserInformation user = daoUser.findByUsername(userName);
			daoUser.remove(user);

			util.userPool(userName, null);

			return new ResponseEntity<String>(HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
