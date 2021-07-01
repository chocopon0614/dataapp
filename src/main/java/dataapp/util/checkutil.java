package dataapp.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

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

import com.fasterxml.jackson.core.JsonProcessingException;

@ConfigurationProperties("app")
public class checkutil {
	private static String introspectionUrl;
	private static String clientId;

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
}