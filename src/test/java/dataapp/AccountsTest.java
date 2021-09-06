package dataapp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
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

import dataapp.dao.UserDatabloodDao;
import dataapp.dao.UserInformationDao;
import dataapp.entity.UserInformation;

@ExtendWith(MockitoExtension.class)
public class AccountsTest {
	@Mock
	private Util util;

	@Mock
	private UserInformationDao daoUser;

	@Mock
	private UserDatabloodDao daoBlood;

	@InjectMocks
	private Accounts accountsController;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void logintestNormal() {

		UserInformation user = new UserInformation();
		user.setPassword("dummyHashedPassword");

		try {
			doReturn("dummyHashedPassword").when(util).getSha256(Mockito.anyString());
			doReturn(user).when(daoUser).findByUsername(Mockito.anyString());
			doReturn("dummyJwt").when(util).createJwt(Mockito.anyString(), Mockito.anyString());

			String expected = "{\"jwt\" : \"dummyJwt\" }";

			ResponseEntity<String> result = accountsController.login("dummyUser", "dummyPassword");
			assertEquals(HttpStatus.OK, result.getStatusCode());
			assertNotNull(result.getBody());
			assertEquals(expected, result.getBody());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	void logintestPasswordError() {

		UserInformation user = new UserInformation();
		user.setPassword("errorHashedPassword");

		try {
			doReturn("dummyHashedPassword").when(util).getSha256(Mockito.anyString());
			doReturn(user).when(daoUser).findByUsername(Mockito.anyString());

			ResponseEntity<String> result = accountsController.login("dummyUser", "dummyPassword");
			assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	void logintestException() {

		try {
			doReturn("dummyHashedPassword").when(util).getSha256(Mockito.anyString());
			doThrow(new NonUniqueResultException()).when(daoUser).findByUsername(Mockito.anyString());

			ResponseEntity<String> result = accountsController.login("dummyUser", "dummyPassword");
			assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	void registertestNormal() {

		try {
			doReturn("dummyHashedPassword").when(util).getSha256(Mockito.anyString());
			doNothing().when(daoUser).persist(Mockito.any());
			doReturn(new UserInformation()).when(daoUser).findByUsername(Mockito.anyString());
			doNothing().when(daoBlood).persist(Mockito.any());

			ResponseEntity<String> result = accountsController.register("dummyUser", "dummyPassword");
			assertEquals(HttpStatus.OK, result.getStatusCode());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	void registertestExceptionUser() {

		try {
			doReturn("dummyHashedPassword").when(util).getSha256(Mockito.anyString());
			doThrow(new EntityExistsException()).when(daoUser).persist(Mockito.any());

			ResponseEntity<String> result = accountsController.register("dummyUser", "dummyPassword");
			assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void registertestExceptionBlood() {

		try {
			doReturn("dummyHashedPassword").when(util).getSha256(Mockito.anyString());
			doNothing().when(daoUser).persist(Mockito.any());
			doReturn(new UserInformation()).when(daoUser).findByUsername(Mockito.anyString());
			doThrow(new EntityExistsException()).when(daoBlood).persist(Mockito.any());

			ResponseEntity<String> result = accountsController.register("dummyUser", "dummyPassword");
			assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	void deletetestNormal() {

		try {
			doReturn("dummyUsername").when(util).varifyJwt(Mockito.anyString(), Mockito.anyString());
			doReturn(new UserInformation()).when(daoUser).findByUsername(Mockito.anyString());
			doNothing().when(daoUser).remove(Mockito.any());

			ResponseEntity<String> result = accountsController.delete("dummyJwt");
			assertEquals(HttpStatus.OK, result.getStatusCode());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	void deletetestException() {

		try {
			doReturn("dummyUsername").when(util).varifyJwt(Mockito.anyString(), Mockito.anyString());
			doReturn(new UserInformation()).when(daoUser).findByUsername(Mockito.anyString());
			doThrow(new EntityNotFoundException()).when(daoUser).remove(Mockito.any());

			ResponseEntity<String> result = accountsController.delete("dummyJwt");
			assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
