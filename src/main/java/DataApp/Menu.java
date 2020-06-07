package DataApp;

import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import DataApp.entity.Userdata;
import DataApp.entity.Userinformation;
import DataApp.util.jwtutil;

@Path("/menu")
public class Menu {
	
	@POST
    public Response tabledata(@FormParam("jwt") final String jwt ) throws JsonProcessingException {
		
	try{

		String UserName = jwtutil.varifyJWT(jwt);
		
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

}
