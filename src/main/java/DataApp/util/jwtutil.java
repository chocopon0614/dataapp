package DataApp.util;

import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

public class jwtutil {

    public static String createJWT(String username){
        long timeout = 60* 30 * 1000;
        String token = "";
        
        try {
            Algorithm algorithm = Algorithm.HMAC256("secret");
            
            token = JWT.create()
                .withIssuer("DataApp")
                .withSubject("login_auth")
                .withExpiresAt(new Date(System.currentTimeMillis() + timeout))
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withClaim("username", username)
                .sign(algorithm);
        } catch (JWTCreationException exception){
            //Invalid Signing configuration / Couldn't convert Claims.
        }

        return token;
    }
    
    public static String varifyJWT(String token) throws Exception {

    	try {
    	    Algorithm algorithm = Algorithm.HMAC256("secret");
    	    
    	    JWTVerifier verifier = JWT.require(algorithm)
   	        .build(); 

    	    DecodedJWT jwt = verifier.verify(token);
    	    String username = jwt.getClaim("username").asString();
    	    
    	    return username;
    	    
    	} catch (JWTVerificationException exception){
    		throw exception;
    	}
    	
   }
}
