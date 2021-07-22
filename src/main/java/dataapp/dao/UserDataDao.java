package dataapp.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.springframework.stereotype.Repository;

import dataapp.entity.UserData;
import dataapp.entity.UserInformation;

@Repository
public class UserDataDao extends AbstractDao {

	public UserData find(int id) {
		EntityManager em = getEm();
		return em.find(UserData.class, id);

	}

	public void persist(UserData data) throws Exception {
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

	public void remove(UserData data) throws Exception {
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
		}

		em.close();

	}

	public List<UserData> findByUserid(UserInformation user) {
		EntityManager em = getEm();
		List<UserData> userDataList = em.createNamedQuery("UserData.findByUserid", UserData.class).setParameter(1, user)
				.getResultList();

		em.close();

		return userDataList;
	}

	public List<UserData> findByUseridSelected(UserInformation user) {
		EntityManager em = getEm();
		List<UserData> userDataList = em.createNamedQuery("UserData.findUseridSelected", UserData.class)
				.setParameter(1, user).getResultList();

		em.close();

		return userDataList;
	}

}