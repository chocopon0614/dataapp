package dataapp.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.springframework.stereotype.Repository;

import dataapp.entity.UserInformation;

@Repository
public class UserInformationDao extends AbstractDao {

	public UserInformation find(int id) {
		EntityManager em = getEm();
		return em.find(UserInformation.class, id);

	}

	public void persist(UserInformation data) {
		EntityManager em = getEm();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();
			em.persist(data);
			tx.commit();
		} catch (Exception e) {
			if (tx != null && tx.isActive())
				tx.rollback();
		}

		em.close();

	}

	public void remove(UserInformation data) {
		EntityManager em = getEm();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();
			em.remove(data);
			tx.commit();
		} catch (Exception e) {
			if (tx != null && tx.isActive())
				tx.rollback();
		}

		em.close();

	}

	public UserInformation findByUsername(String userName) {
		EntityManager em = getEm();
		UserInformation user = em.createNamedQuery("UserInformation.findByUsername", UserInformation.class)
				.setParameter(1, userName).getSingleResult();

		em.close();

		return user;
	}
}