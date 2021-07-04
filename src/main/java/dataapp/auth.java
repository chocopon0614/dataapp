package dataapp;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dataapp.entity.userinformation;

@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class auth {

	@PostMapping("authentication")
	public ResponseEntity<String> authentication(@RequestParam("jwt") final String jwt) {

		String username = util.varifyjwt(jwt, "username");
		String password = util.varifyjwt(jwt, "password");

		String res = "{\"username\" : \"" + username + "\" , \"password\" : \"" + password + "\"}";
		return new ResponseEntity<String>(res, HttpStatus.OK);

	}

	@GetMapping("authorization")
	public ResponseEntity<String> authorization(@RequestHeader("authorization") final String authheader) {

		Charset charset = StandardCharsets.UTF_8;
		String tmp = authheader.split(" ")[1];

		byte[] b = Base64.getDecoder().decode(tmp.getBytes(charset));
		String de1 = new String(b, charset);

		String userName = de1.split(":")[0];
		String hashedPassword = de1.split(":")[1];

		userinformation user = util.getuser(userName);
		String dbPassword = user.getPassword();

		if (hashedPassword.equals(dbPassword)) {
			return new ResponseEntity<String>(HttpStatus.OK);

		} else {
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);

		}

	}

}
