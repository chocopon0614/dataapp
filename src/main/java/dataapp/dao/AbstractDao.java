package dataapp.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.springframework.stereotype.Repository;

@Repository
public class AbstractDao {

	public static EntityManager getEm() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("DataApp");
		EntityManager em = emf.createEntityManager();
		return em;
	}

}