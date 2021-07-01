package dataapp.util;

import java.util.Date;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

@ConfigurationProperties("app")
public class jwtutil {
	private static long jwtExpiredTime;

	public static String createjwt(String username, String password) {
		Algorithm algorithm = Algorithm.HMAC256("secret");

		String token = JWT.create().withIssuer("dataapp").withSubject("loginauth")
				.withExpiresAt(new Date(System.currentTimeMillis() + jwtExpiredTime))
				.withIssuedAt(new Date(System.currentTimeMillis())).withClaim("username", username)
				.withClaim("password", password).sign(algorithm);

		return token;
	}

	public static String varifyjwt(String token, String key) {
		Algorithm algorithm = Algorithm.HMAC256("secret");

		JWTVerifier verifier = JWT.require(algorithm).build();
		DecodedJWT jwt = verifier.verify(token);

		return jwt.getClaim(key).asString();

	}
}
