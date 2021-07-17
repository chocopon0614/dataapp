
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import dataapp.Menu;
import dataapp.Util;
import dataapp.entity.UserInformation;

public class dataservicetest {

	@InjectMocks
	private Menu menuservice = new Menu();

	@Autowired
	private Util util;

	@Test
	void test1() {
		MockedStatic<Util> mocked = Mockito.mockStatic(Util.class);

		mocked.when(() -> {
			util.varifyJwt(Mockito.anyString(), Mockito.anyString());
		}).thenReturn("dummy");

		EntityManagerFactory emf = Mockito.mock(EntityManagerFactory.class);
		EntityManager em = Mockito.mock(EntityManager.class);

		MockedStatic<Persistence> mocked_persitence = Mockito.mockStatic(Persistence.class);
		mocked_persitence.when(() -> {
			Persistence.createEntityManagerFactory(Mockito.anyString());
		}).thenReturn(emf);
		Mockito.when(emf.createEntityManager()).thenReturn(em);

		TypedQuery<Object> mockQuery = mock(TypedQuery.class);

		Mockito.when(em.createNamedQuery(Mockito.anyString(), Mockito.any())).thenReturn(mockQuery);
		Mockito.when(mockQuery.setParameter(Mockito.anyInt(), Mockito.any())).thenReturn(mockQuery);
		when(mockQuery.getSingleResult()).thenReturn(new UserInformation());
		when(mockQuery.getResultList()).thenReturn(new ArrayList<>());

		try {
			ResponseEntity<String> result1 = menuservice.bodyData("dummy");
			assertEquals(HttpStatus.OK, result1.getStatusCode());
			assertNotNull(result1.getBody());

			mocked.close();
			mocked_persitence.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	void test2() {
		MockedStatic<Util> mocked = Mockito.mockStatic(Util.class);

		mocked.when(() -> {
			util.varifyJwt(Mockito.anyString(), Mockito.anyString());
		}).thenReturn("dummy");

		EntityManagerFactory emf = Mockito.mock(EntityManagerFactory.class);
		EntityManager em = Mockito.mock(EntityManager.class);

		MockedStatic<Persistence> mocked_persitence = Mockito.mockStatic(Persistence.class);
		mocked_persitence.when(() -> {
			Persistence.createEntityManagerFactory(Mockito.anyString());
		}).thenReturn(emf);
		Mockito.when(emf.createEntityManager()).thenReturn(em);

		TypedQuery<Object> mockQuery = mock(TypedQuery.class);

		Mockito.when(em.createNamedQuery(Mockito.anyString(), Mockito.any())).thenReturn(mockQuery);
		Mockito.when(mockQuery.setParameter(Mockito.anyInt(), Mockito.any())).thenReturn(mockQuery);
		when(mockQuery.getSingleResult()).thenReturn(new UserInformation());
		when(mockQuery.getResultList()).thenReturn(null);

		try {
			ResponseEntity<String> result2 = menuservice.bodyData("dummy");
			assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result2.getStatusCode());
			assertNull(result2.getBody());

			mocked.close();
			mocked_persitence.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
