package DataApp;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import DataApp.entity.Userdata;
import DataApp.entity.Userdatablood;
import DataApp.entity.Userinformation;
import DataApp.util.jwtutil;

@Path("/menu")
public class Menu {
	
	@POST
	@Path("/BodyData")
    public Response tabledata(@FormParam("jwt") final String jwt ) throws JsonProcessingException {
		
	try{

		String UserName = jwtutil.varifyJWT(jwt,"username");
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("DataApp");
		EntityManager em = emf.createEntityManager();
		
		Userinformation UserObj = em.createNamedQuery("Userinformation.findbyusername",Userinformation.class)
				.setParameter(1, UserName)
				.getSingleResult();
		
		List<Userdata> UserDataList = em.createNamedQuery("Userdata.findUserid_desc", Userdata.class)
				.setParameter(1, UserObj)
				.getResultList();
		
		if (!Objects.isNull(UserDataList)) {
			ObjectMapper mapper = new ObjectMapper();
			String ResJson = mapper.writeValueAsString(UserDataList);

			Response response = Response.ok().entity(ResJson).
					type(MediaType.APPLICATION_JSON).build();

			return response;
			    
			} else {
				Response response = Response.status(500).build();
			    return response;
			}
			
	  }catch(Exception e){
		Response response = Response.status(500).build();
		return response;
		
	}
 
 }
	
	@DELETE
	@Path("/trash")
    public Response datatrash(@FormParam("jwt") final String jwt, @FormParam("id") final int id )  {
		
	try{

		EntityTransaction tx = null;
		String UserName = jwtutil.varifyJWT(jwt,"username");
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("DataApp");
		EntityManager em = emf.createEntityManager();
		
		Userinformation UserObj = em.createNamedQuery("Userinformation.findbyusername",Userinformation.class)
				.setParameter(1, UserName)
				.getSingleResult();
		
	    tx = em.getTransaction();
	    tx.begin();

		em.createNamedQuery("Userdata.deleteData",Userdata.class)
				.setParameter(1, UserObj)
				.setParameter(2, id)
				.executeUpdate();
	    
	    tx.commit();

		Response response = Response.ok().build();
		return response;
		
			
	  }catch(Exception e){
		Response response = Response.status(500).build();
		return response;
		
	}
 
 }	

	
	@POST
	@Path("/insertdata")
    public Response insertdata(@FormParam("jwt") final String jwt, @FormParam("height") final double height, 
    		@FormParam("weight") final double weight) {
		
	try{

		EntityTransaction tx = null;
		String UserName = jwtutil.varifyJWT(jwt,"username");
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("DataApp");
		EntityManager em = emf.createEntityManager();
		
		Userinformation UserObj = em.createNamedQuery("Userinformation.findbyusername",Userinformation.class)
				.setParameter(1, UserName)
				.getSingleResult();
		
	    tx = em.getTransaction();
	    tx.begin();
	    
	    Userdata userdata = new Userdata();
	    userdata.setUserinformation(UserObj);
	    userdata.setHeight(height);
	    userdata.setWeight(weight);
	    
	    long millis = System.currentTimeMillis();
	    userdata.setCreateTime(new Timestamp(millis));
	    userdata.setModifiedTime(new Timestamp(millis));

	    em.persist(userdata);
	    
	    tx.commit();

	    
		List<Userdata> UserDataList = em.createNamedQuery("Userdata.findUserid_desc", Userdata.class)
				.setParameter(1, UserObj)
				.getResultList();


		ObjectMapper mapper = new ObjectMapper();
		String ResJson = mapper.writeValueAsString(UserDataList);

		Response response = Response.ok().entity(ResJson).
				type(MediaType.APPLICATION_JSON).build();

		return response;
		
			
	  }catch(Exception e){
		Response response = Response.status(500).build();
		return response;
		
	}
 
  }
	
	@POST
	@Path("/BloodData")
    public Response blooddata(@FormParam("jwt") final String jwt ) throws JsonProcessingException {
		
	try{

		String UserName = jwtutil.varifyJWT(jwt,"username");
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("DataApp");
		EntityManager em = emf.createEntityManager();
		
		Userinformation UserObj = em.createNamedQuery("Userinformation.findbyusername",Userinformation.class)
				.setParameter(1, UserName)
				.getSingleResult();
		
		List<Userdatablood> UserDataList = em.createNamedQuery("Userdatablood.findUserid", Userdatablood.class)
				.setParameter(1, UserObj)
				.getResultList();
		
		if (!Objects.isNull(UserDataList)) {
			ObjectMapper mapper = new ObjectMapper();
			String ResJson = mapper.writeValueAsString(UserDataList);

			Response response = Response.ok().entity(ResJson).
					type(MediaType.APPLICATION_JSON).build();

			return response;
			    
			} else {
				Response response = Response.status(500).build();
			    return response;
			}
			
	  }catch(Exception e){
		Response response = Response.status(500).build();
		return response;
		
	}
 
 }

	@POST
	@Path("/updatedata")
    public Response updatedata(@FormParam("jwt") final String jwt, 
    		@FormParam("newvalue") final double newvalue, 
    		@FormParam("bloodname") final String bloodname) throws JsonProcessingException {
		
	try{

		String UserName = jwtutil.varifyJWT(jwt,"username");
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("DataApp");
		EntityManager em = emf.createEntityManager();
		
		Userinformation UserObj = em.createNamedQuery("Userinformation.findbyusername",Userinformation.class)
				.setParameter(1, UserName)
				.getSingleResult();
		
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
		String QueryName = "Userdatablood.update_"+ bloodname;

		em.createNamedQuery(QueryName, Userdatablood.class)
				.setParameter(1, newvalue)
				.setParameter(2, UserObj)
				.executeUpdate();
		
		tx.commit();
		
		Response response = Response.ok().build();

		return response;
			
	  }catch(Exception e){
		Response response = Response.status(500).build();
		return response;
		
	}
 
  }
	

}
