package dataapp.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.springframework.stereotype.Repository;

import dataapp.entity.UserDatablood;
import dataapp.entity.UserInformation;

@Repository
public class UserDatabloodDao extends AbstractDao {

	public void persist(UserDatablood data) {
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

	public void remove(UserDatablood data) {
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

	public UserDatablood findByUserid(UserInformation user) {
		EntityManager em = getEm();
		UserDatablood userData = em.createNamedQuery("UserDatablood.findByUserid", UserDatablood.class)
				.setParameter(1, user).getSingleResult();

		em.close();

		return userData;
	}

	public void update(String item, double value, UserDatablood userData) {

		switch (item) {
		case "Tg":
			userData.setTg(value);
		case "Gtp":
			userData.setTg(value);
		case "Hdl":
			userData.setTg(value);
		case "Ldl":
			userData.setTg(value);
		case "Fpg":
			userData.setTg(value);

		}

	}

}