package DataApp;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import DataApp.entity.Userdata;
import DataApp.entity.Userdatablood;
import DataApp.entity.Userinformation;
import DataApp.util.jwtutil;

@Path("/Open")
public class OpenApi {

	@Path("/ChartData")
    @GET
    public Response chartdata(@Context HttpHeaders headers) throws JsonProcessingException {
		
		String UserName = headers.getRequestHeader("resource-owner").get(0);
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("DataApp");
		EntityManager em = emf.createEntityManager();
		
		Userinformation userobj = em.createNamedQuery("Userinformation.findbyusername",Userinformation.class)
				.setParameter(1, UserName)
				.getSingleResult();

		List<Userdatablood> userdata_list = em.createNamedQuery("Userdatablood.findUserid", Userdatablood.class)
				.setParameter(1, userobj)
				.getResultList();
		
		if (!Objects.isNull(userdata_list)) {
			ObjectMapper mapper = new ObjectMapper();
			String resjson = mapper.writeValueAsString(userdata_list);

			Response response = Response.ok().entity(resjson).
					type(MediaType.APPLICATION_JSON).build();

			return response;
			    
			} else {
				Response response = Response.status(401).build();
			    return response;
			
			}
		}
	
	@Path("/UserInfo")
    @GET
    public Response userinfo(@Context HttpHeaders headers) throws JsonProcessingException {
		
		String UserName = headers.getRequestHeader("resource-owner").get(0);
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("DataApp");
		EntityManager em = emf.createEntityManager();
		
		Userinformation userobj = em.createNamedQuery("Userinformation.findbyusername",Userinformation.class)
				.setParameter(1, UserName)
				.getSingleResult();

		List<Userdata> userdata_list = em.createNamedQuery("Userdata.findUserid_selected", Userdata.class)
				.setParameter(1, userobj)
				.getResultList();
		
		if (!Objects.isNull(userdata_list)) {
			ObjectMapper mapper = new ObjectMapper();
			String resjson = mapper.writeValueAsString(userdata_list);

			Response response = Response.ok().entity(resjson).
					type(MediaType.APPLICATION_JSON).build();

			return response;
			    
			} else {
				Response response = Response.status(401).build();
			    return response;
			
			}
		}

	@Path("/Verification")
    @GET
    public Response verification(@Context HttpHeaders headers) {
		
		String ConfirmationCode = headers.getRequestHeader("Authorization").get(0).split(" ")[1];

		Charset charset = StandardCharsets.UTF_8;

		byte[] dec = Base64.getDecoder().decode(ConfirmationCode);
		String decstr = new String(dec,charset);

		String UserName = decstr.split(":")[0];
		String PassWord = decstr.split(":")[1];

		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("DataApp");
		EntityManager em = emf.createEntityManager();
		
		Userinformation UserObj = em.createNamedQuery("Userinformation.findbyusername",Userinformation.class)
				.setParameter(1, UserName)
				.getSingleResult();

		if (!Objects.isNull(UserObj)) {
			String DbPass = UserObj.getPassword();
			if (PassWord.equals(DbPass)) {

			Response response = Response.ok().build();
			return response;
			    
			} else {
				
				Response response = Response.status(401).build();
			    return response;
			}
		}else {

		  Response response = Response.status(500).build();
		  return response;

		}
    }
	
	@Path("/Authorization")
    @POST
    public Response authorization(@FormParam("jwt") final String jwt ) throws Exception {

	try{
		
		String username = jwtutil.varifyJWT(jwt, "username");
		String password = jwtutil.varifyJWT(jwt, "password");
		
		String res = "{\"username\" : \"" + username + "\" , \"password\" : \"" + password + "\"}";

	    ResponseBuilder rb = Response.ok().type(MediaType.APPLICATION_JSON_TYPE);
		return rb.entity(res).build();

    }catch(Exception e) {
    	throw e;

	  }
    }


}
