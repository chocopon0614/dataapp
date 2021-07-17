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

	public void persist(UserData data) {
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

	public void remove(UserData data) {
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

	public List<UserData> findByUserid(UserInformation user) {
		EntityManager em = getEm();
		List<UserData> userDataList = em.createNamedQuery("UserData.findByUserid", UserData.class).setParameter(1, user)
				.getResultList();

		em.close();

		return userDataList;
	}

//	public List<UserData> findUseridDesc(UserInformation user) {
//		EntityManager em = getEm();
//		List<UserData> userDataList = em.createNamedQuery("UserData.findUseridDesc", UserData.class)
//				.setParameter(1, user).getResultList();
//
//		em.close();
//
//		return userDataList;
//	}

	public List<UserData> findByUseridSelected(UserInformation user) {
		EntityManager em = getEm();
		List<UserData> userDataList = em.createNamedQuery("UserData.findUseridSelected", UserData.class)
				.setParameter(1, user).getResultList();

		em.close();

		return userDataList;
	}

//	public UserData selectData(UserInformation user, int id) {
//		EntityManager em = getEm();
//		UserData userData = em.createNamedQuery("UserData.selectData", UserData.class).setParameter(1, user)
//				.setParameter(2, id).getSingleResult();
//
//		em.close();
//
//		return userData;
//	}
//
//	public void deleteData(UserInformation user, int id) {
//		EntityManager em = getEm();
//		em.createNamedQuery("UserData.deleteData", UserData.class).setParameter(1, user).setParameter(2, id)
//				.executeUpdate();
//
//		em.close();
//
//	}

}