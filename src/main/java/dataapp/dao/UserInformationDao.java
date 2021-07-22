package dataapp.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.springframework.stereotype.Repository;

import dataapp.entity.UserInformation;

@Repository
public class UserInformationDao extends AbstractDao {

	public void persist(UserInformation data) throws Exception {
		EntityManager em = getEm();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();
			em.persist(data);
			tx.commit();
		} catch (Exception e) {
			if (tx != null && tx.isActive())
				tx.rollback();

			throw e;
		}

		em.close();

	}

	public void remove(UserInformation data) throws Exception {
		EntityManager em = getEm();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();
			if (!em.contains(data)) {
				data = em.merge(data);
			}

			em.remove(data);
			tx.commit();
		} catch (Exception e) {
			if (tx != null && tx.isActive())
				tx.rollback();

			throw e;
		} finally {
			em.close();
		}
	}

	public UserInformation findByUsername(String userName) {
		EntityManager em = getEm();
		UserInformation user = em.createNamedQuery("UserInformation.findByUsername", UserInformation.class)
				.setParameter(1, userName).getSingleResult();

		em.close();

		return user;
	}
}