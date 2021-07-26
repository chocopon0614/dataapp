
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

import dataapp.Accounts;
import dataapp.Util;
import dataapp.dao.UserInformationDao;
import dataapp.entity.UserInformation;

@ExtendWith(MockitoExtension.class)
public class AccountsTest {
	@Mock
	private Util util;

	@Mock
	private UserInformationDao daoUser;

	@Mock
	private UserInformation dummyUser;

	@InjectMocks
	private Accounts accountsController = new Accounts();

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void test1() {

		UserInformation user = new UserInformation();
		user.setPassword("dummyHashedPassword");

		try {
			doReturn("dummyJwt").when(util).createJwt(Mockito.anyString(), Mockito.anyString());
			doReturn("dummyHashedPassword").when(util).getSha256(Mockito.anyString());
			doReturn(user).when(daoUser).findByUsername(Mockito.anyString());

			String expected = "{\"jwt\" : \"dummyJwt\" }";

			ResponseEntity<String> result1 = accountsController.login("dummyUser", "dummyPassword");
			assertEquals(HttpStatus.OK, result1.getStatusCode());
			assertNotNull(result1.getBody());
			assertEquals(expected, result1.getBody());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
