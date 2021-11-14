package dataapp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import dataapp.dto.Properties;

@ExtendWith(MockitoExtension.class)
public class UtilTest {
	@Mock
	private Properties prop;

	@Mock
	private RestTemplate restTemplate;

	@InjectMocks
	private Util utilController;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void tokencheckTestNormal() {

		ResponseEntity<String> dummyRes = new ResponseEntity<>("testRes", HttpStatus.OK);

		try {
			doReturn("dummyClientId").when(prop).getClientId();
			doReturn("dummyUrl").when(prop).getIntrospectionUrl();
			doReturn(dummyRes).when(restTemplate).postForEntity(Mockito.anyString(), Mockito.any(), Mockito.any());

			String result = utilController.tokenCheck("dummyToken");
			assertEquals("testRes", result);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	void getSha256TestNormal() {

		try {
			String result = utilController.getSha256("dummyString");
			assertEquals("37b0f902984de6eca6fcdc4a512680e4915f8d85d5295b2043ec4cafdc9d18b4", result);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	void createJwt_varifyJwtTestNormal() {

		doReturn((long) 3600).when(prop).getJwtExpiredTime();

		try {
			String result1 = utilController.createJwt("dummyUsername", "dummyPassword");
			String result2 = utilController.varifyJwt(result1, "userName");
			assertEquals("dummyUsername", result2);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
