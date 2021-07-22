package dataapp;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dataapp.dao.UserInformationDao;
import dataapp.entity.UserInformation;

@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class Auth {
	@Autowired
	private Util util;

	@Autowired
	private UserInformationDao daoUser;

	@PostMapping("authentication")
	public ResponseEntity<String> authentication(@RequestParam("jwt") final String jwt) {

		String userName = util.varifyJwt(jwt, "userName");
		String passWord = util.varifyJwt(jwt, "passWord");

		String res = "{\"userName\" : \"" + userName + "\" , \"passWord\" : \"" + passWord + "\"}";
		return new ResponseEntity<String>(res, HttpStatus.OK);

	}

	@GetMapping("authorization")
	public ResponseEntity<String> authorization(@RequestHeader("authorization") final String authorization) {

		try {

			Charset charSet = StandardCharsets.UTF_8;
			String tmp = authorization.split(" ")[1];

			byte[] b = Base64.getDecoder().decode(tmp.getBytes(charSet));
			String de = new String(b, charSet);

			String userName = de.split(":")[0];
			String hashedPassword = de.split(":")[1];

			UserInformation user = daoUser.findByUsername(userName);
			String dbPassword = user.getPassword();

			System.out.println("！！！！！" + userName);
			System.out.println("！！！！！" + hashedPassword);
			System.out.println("！！！！！" + dbPassword);

			if (hashedPassword.equals(dbPassword)) {
				return new ResponseEntity<String>(HttpStatus.OK);

			} else {
				return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);

			}

		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
