package dataapp.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.springframework.stereotype.Repository;

import dataapp.entity.UserDatablood;
import dataapp.entity.UserInformation;

@Repository
public class UserDatabloodDao extends AbstractDao {

	public void persist(UserDatablood data) throws Exception {
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

	public void update(String item, double value, UserDatablood data) throws Exception {
	
		EntityManager em = getEm();
		EntityTransaction tx = em.getTransaction();
	
		try {
			tx.begin();
			if (!em.contains(data)) {
				data = em.merge(data);
			}
	
			switch (item) {
			case "TG":
				data.setTg(value);
				break;
			case "GTP":
				data.setGtp(value);
				break;
			case "HDL":
				data.setHdl(value);
				break;
			case "LDL":
				data.setLdl(value);
				break;
			case "FPG":
				data.setFpg(value);
				break;
			}
	
			tx.commit();
		} catch (Exception e) {
			if (tx != null && tx.isActive())
				tx.rollback();
	
			throw e;
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

}