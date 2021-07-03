package dataapp;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

import dataapp.entity.userinformation;

@ConfigurationProperties("app")
public class util {
	private static String introspectionUrl;
	private static String clientId;
	private static long jwtExpiredTime;

	public static Map<String, String> validdheck(BindingResult result) {

		Map<String, String> map = new HashMap<>();

		for (ObjectError error : result.getAllErrors()) {
			String temp = error.getDefaultMessage();
			map.put(temp.split(":")[0], temp.split(":")[1]);
		}

		return map;
	}

	public static String tokencheck(String token) throws JsonProcessingException {

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("client_id", clientId);
		map.add("token", token);
		map.add("token_type_hint", "access_token");

		HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);
		ResponseEntity<String> res = restTemplate.postForEntity(introspectionUrl, entity, String.class);

		return res.getBody();

	}

	public static String getsha256(String input) throws NoSuchAlgorithmException, UnsupportedEncodingException {

		String toReturn = null;
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		digest.reset();
		digest.update(input.getBytes("utf8"));
		toReturn = String.format("%064x", new BigInteger(1, digest.digest()));

		return toReturn;
	}

	public static String createjwt(String username, String password) {
		Algorithm algorithm = Algorithm.HMAC256("secret");

		String token = JWT.create().withIssuer("dataapp").withSubject("loginauth")
				.withExpiresAt(new Date(System.currentTimeMillis() + jwtExpiredTime))
				.withIssuedAt(new Date(System.currentTimeMillis())).withClaim("username", username)
				.withClaim("password", password).sign(algorithm);

		return token;
	}

	public static String varifyjwt(String token, String key) {
		Algorithm algorithm = Algorithm.HMAC256("secret");

		JWTVerifier verifier = JWT.require(algorithm).build();
		DecodedJWT jwt = verifier.verify(token);

		return jwt.getClaim(key).asString();

	}

	public static String getdbpassword(String username) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("dataapp");
		EntityManager em = emf.createEntityManager();

		userinformation user = em.createNamedQuery("userinformation.findbyusername", userinformation.class)
				.setParameter(1, username).getSingleResult();

		if (!(user == null))
			return user.getPassword();
		else {
			return "";
		}
	}

}