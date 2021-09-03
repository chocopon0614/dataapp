package dataapp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

import javax.persistence.NonUniqueResultException;

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

import dataapp.dao.UserInformationDao;
import dataapp.entity.UserInformation;

@ExtendWith(MockitoExtension.class)
public class AuthTest {
	@Mock
	private Util util;

	@Mock
	private UserInformationDao daoUser;

	@InjectMocks
	private Auth authController;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void authenticationTestNormal() {

		try {
			doReturn("dummyUsername").when(util).varifyJwt(Mockito.anyString(), Mockito.eq("userName"));
			doReturn("dummyPassword").when(util).varifyJwt(Mockito.anyString(), Mockito.eq("passWord"));

			String expected = "{\"userName\" : \"" + "dummyUsername" + "\" , \"passWord\" : \"" + "dummyPassword"
					+ "\"}";

			ResponseEntity<String> result = authController.authentication("dummyJwt");
			assertEquals(HttpStatus.OK, result.getStatusCode());
			assertNotNull(result.getBody());
			assertEquals(expected, result.getBody());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	void authorizationTestNormal() {

		UserInformation user = new UserInformation();
		user.setUsername("dummyUsername");
		user.setPassword("dummyPassword");

		try {
			doReturn(user).when(daoUser).findByUsername(Mockito.anyString());

			ResponseEntity<String> result = authController.authorization("Basic ZHVtbXlVc2VybmFtZTpkdW1teVBhc3N3b3Jk");
			assertEquals(HttpStatus.OK, result.getStatusCode());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	void authorizationTestError() {

		UserInformation user = new UserInformation();
		user.setUsername("dummyUsername");
		user.setPassword("errorPassword");

		try {
			doReturn(user).when(daoUser).findByUsername(Mockito.anyString());

			ResponseEntity<String> result = authController.authorization("Basic ZHVtbXlVc2VybmFtZTpkdW1teVBhc3N3b3Jk");
			assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	void authorizationTestException() {

		try {
			doThrow(new NonUniqueResultException()).when(daoUser).findByUsername(Mockito.anyString());

			ResponseEntity<String> result = authController.authorization("Basic ZHVtbXlVc2VybmFtZTpkdW1teVBhc3N3b3Jk");
			assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
