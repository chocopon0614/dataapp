package dataapp.util;

import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

public class jwtutil {

	public static String createjwt(String username, String password) {
		long timeout = 60 * 30 * 1000;
		String token = "";

		try {
			Algorithm algorithm = Algorithm.HMAC256("secret");

			token = JWT.create().withIssuer("DataApp").withSubject("login_auth")
					.withExpiresAt(new Date(System.currentTimeMillis() + timeout))
					.withIssuedAt(new Date(System.currentTimeMillis())).withClaim("username", username)
					.withClaim("password", password).sign(algorithm);
		} catch (JWTCreationException exception) {
			// Invalid Signing configuration / Couldn't convert Claims.
		}

		return token;
	}

	public static String varifyjwt(String token, String key) throws Exception {

		try {
			Algorithm algorithm = Algorithm.HMAC256("secret");

			JWTVerifier verifier = JWT.require(algorithm).build();

			DecodedJWT jwt = verifier.verify(token);
			return jwt.getClaim(key).asString();

		} catch (JWTVerificationException exception) {
			throw exception;
		}

	}
}
