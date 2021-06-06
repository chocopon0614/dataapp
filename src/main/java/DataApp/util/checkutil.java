package DataApp.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.client.RestTemplate;

public class checkutil {
	public static Map<String, String> validCheck(BindingResult result) {

		Map<String, String> map = new HashMap<>();

		for (ObjectError error : result.getAllErrors()) {
			String temp = error.getDefaultMessage();
			map.put(temp.split(":")[0], temp.split(":")[1]);
		}

		return map;
	}

	public static String tokenCheck(String token) {

		RestTemplate restTemplate = new RestTemplate();
		String URL = "https://api.au-syd.apiconnect.appdomain.cloud/chocopon0899gmailcom-dev/sb/oauthprovider/oauth2/introspect";

		HttpHeaders headers = new HttpHeaders();

		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("client_id", "1886b5cd-d923-41db-aff7-2e841997e22b");
		map.add("token", token);
		map.add("token_type_hint", "access_token");

		HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);

		ResponseEntity<String> response = restTemplate.postForEntity(URL, entity, String.class);

		return response.getBody();

	}
}