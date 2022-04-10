package dataapp.dao;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import dataapp.entity.AwsConfig;

@Repository
public class AwsConfigDao extends AbstractDao {

	public AwsConfig find(int id) {
		EntityManager em = getEm();
		return em.find(AwsConfig.class, id);

	}

}