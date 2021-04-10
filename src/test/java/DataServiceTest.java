
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import DataApp.Menu;
import DataApp.util.jwtutil;

public class DataServiceTest {

	@InjectMocks
	private Menu menuservice = new Menu();

	@Test
	void test1() {
		MockedStatic<jwtutil> mocked = Mockito.mockStatic(jwtutil.class);
		mocked.when(() -> {
			jwtutil.varifyJWT(Mockito.anyString(), Mockito.anyString());
		}).thenReturn("test1");

		try {
			ResponseEntity<String> result1 = menuservice.tabledata("dummy");
			assertEquals(HttpStatus.OK, result1.getStatusCode());
			assertNotNull(result1.getBody());

			mocked.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	void test2() {
		MockedStatic<jwtutil> mocked = Mockito.mockStatic(jwtutil.class);
		mocked.when(() -> {
			jwtutil.varifyJWT(Mockito.anyString(), Mockito.anyString());
		}).thenReturn("test2");

		try {
			ResponseEntity<String> result2 = menuservice.tabledata("dummy");
			assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result2.getStatusCode());
			assertNull(result2.getBody());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
