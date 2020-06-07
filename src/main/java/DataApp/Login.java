package DataApp;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import DataApp.entity.Userinformation;
import DataApp.util.jwtutil;

@Path("/login")
public class Login {
	
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	public Response userLogin(@FormParam("username") final String UserName, 
			@FormParam("password") final String PassWord) {
		
//		String hash_password = hashutil.getSHA256(PassWord);


		EntityManagerFactory emf = Persistence.createEntityManagerFactory("DataApp");
		EntityManager em = emf.createEntityManager();
		
		List<Userinformation> UserObj = em.createNamedQuery("Userinformation.findbyusername",Userinformation.class)
				.setParameter(1, UserName)
				.getResultList();
		
		if (!(UserObj == null || UserObj.size() == 0)) {
			String DbPass = UserObj.get(0).getPassword();
//			if (hash_password.equals(DbPass)) {
			if (PassWord.equals(DbPass)) {
				
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

 }
