package dataapp;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.client.RestTemplate;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonProcessingException;

import dataapp.dto.Properties;

@Component
public class Util {
	@Autowired
	private Properties prop;

	public Map<String, String> validCheck(BindingResult result) {

		Map<String, String> map = new HashMap<>();

		for (ObjectError error : result.getAllErrors()) {
			String temp = error.getDefaultMessage();
			map.put(temp.split(":")[0], temp.split(":")[1]);
		}

		return map;
	}

	public String tokenCheck(String token) throws JsonProcessingException {

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("client_id", prop.getClientId());
		map.add("token", token);
		map.add("token_type_hint", "access_token");

		HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);
		ResponseEntity<String> res = restTemplate.postForEntity(prop.getIntrospectionUrl(), entity, String.class);

		return res.getBody();

	}

	public String getSha256(String input) throws NoSuchAlgorithmException, UnsupportedEncodingException {

		String toReturn = null;
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		digest.reset();
		digest.update(input.getBytes("utf8"));
		toReturn = String.format("%064x", new BigInteger(1, digest.digest()));

		return toReturn;
	}

	public String createJwt(String userName, String passWord) {
		Algorithm algorithm = Algorithm.HMAC256("secret");

		String jwt = JWT.create().withIssuer("DataApp").withSubject("login")
				.withExpiresAt(new Date(System.currentTimeMillis() + prop.getJwtExpiredTime()))
				.withIssuedAt(new Date(System.currentTimeMillis())).withClaim("userName", userName)
				.withClaim("passWord", passWord).sign(algorithm);

		return jwt;
	}

	public String varifyJwt(String token, String key) {
		Algorithm algorithm = Algorithm.HMAC256("secret");

		JWTVerifier verifier = JWT.require(algorithm).build();
		DecodedJWT jwt = verifier.verify(token);

		return jwt.getClaim(key).asString();

	}

}