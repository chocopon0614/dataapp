package DataApp;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import DataApp.entity.Userinformation;
import DataApp.util.hashutil;
import DataApp.util.jwtutil;

@Path("/login")
public class Login {
	
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	public Response userLogin(@FormParam("username") final String UserName, 
			@FormParam("password") final String PassWord) {
		
		String hash_password = hashutil.getSHA256(PassWord);


		EntityManagerFactory emf = Persistence.createEntityManagerFactory("DataApp");
		EntityManager em = emf.createEntityManager();
		
		List<Userinformation> UserObj = em.createNamedQuery("Userinformation.findbyusername",Userinformation.class)
				.setParameter(1, UserName)
				.getResultList();
		
		if (!(UserObj == null || UserObj.size() == 0)) {
			String DbPass = UserObj.get(0).getPassword();
			if (hash_password.equals(DbPass)) {
				
				String jwttoken = jwtutil.createJWT(UserName);
				String res = "{\"JWT\" : \"" + jwttoken + "\" }";

			    ResponseBuilder rb = Response.ok().type(MediaType.APPLICATION_JSON_TYPE);
				return rb.entity(res).build();
				
			} else {

				Response response = Response.status(400).build();
			    return response;

			}
				
		}else {
			Response response = Response.status(500).build();
		    return response;
		}
	  }
	
	@POST
	@Path("/register")
	public Response register(@FormParam("username") final String username,
			@FormParam("password") final String password) throws Exception {
		
		String hash_password = hashutil.getSHA256(password);

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("DataApp");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = null;
		
		try {
	      tx = em.getTransaction();
	      tx.begin();

	      Userinformation userinfo = new Userinformation();
	      userinfo.setUsername(username);
	      userinfo.setPassword(hash_password);
	      userinfo.setCreateTime(new Timestamp(System.currentTimeMillis()));
	      userinfo.setModifiedTime(new Timestamp(System.currentTimeMillis()));

	      em.persist(userinfo);
	    
	      tx.commit();

		  Response response = Response.ok().build();
		  return response;

		}catch (RuntimeException e) {
			if ( tx != null && tx.isActive() ) tx.rollback();

			Response response = Response.status(500).build();
			return response;

			
		} finally {
			em.close();
		}
		
	}
	

 }
