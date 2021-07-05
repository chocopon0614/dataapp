
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

import dataapp.menu;
import dataapp.util;
import dataapp.entity.userinformation;

public class dataservicetest {

	@InjectMocks
	private menu menuservice = new menu();

	@Autowired
	private util util;

	@Test
	void test1() {
		MockedStatic<util> mocked = Mockito.mockStatic(util.class);

		mocked.when(() -> {
			util.varifyjwt(Mockito.anyString(), Mockito.anyString());
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
		when(mockQuery.getSingleResult()).thenReturn(new userinformation());
		when(mockQuery.getResultList()).thenReturn(new ArrayList<>());

		try {
			ResponseEntity<String> result1 = menuservice.bodydata("dummy");
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
		MockedStatic<util> mocked = Mockito.mockStatic(util.class);

		mocked.when(() -> {
			util.varifyjwt(Mockito.anyString(), Mockito.anyString());
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
		when(mockQuery.getSingleResult()).thenReturn(new userinformation());
		when(mockQuery.getResultList()).thenReturn(null);

		try {
			ResponseEntity<String> result2 = menuservice.bodydata("dummy");
			assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result2.getStatusCode());
			assertNull(result2.getBody());

			mocked.close();
			mocked_persitence.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
