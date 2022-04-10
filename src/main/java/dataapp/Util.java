package dataapp;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;
import com.amazonaws.services.cognitoidp.model.AdminConfirmSignUpRequest;
import com.amazonaws.services.cognitoidp.model.AdminDeleteUserRequest;
import com.amazonaws.services.cognitoidp.model.SignUpRequest;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import dataapp.dao.AwsConfigDao;
import dataapp.dto.Properties;
import dataapp.entity.AwsConfig;

@Component
public class Util {
	@Autowired
	private Properties prop;

	@Autowired
	private AwsConfigDao daoAws;

	public Map<String, String> validCheck(BindingResult result) {

		Map<String, String> map = new HashMap<>();

		for (ObjectError error : result.getAllErrors()) {
			String temp = error.getDefaultMessage();
			map.put(temp.split(":")[0], temp.split(":")[1]);
		}

		return map;
	}

	public String getSha256(String input) throws NoSuchAlgorithmException, UnsupportedEncodingException {

		String toReturn = null;
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		digest.reset();
		digest.update(input.getBytes("utf8"));
		toReturn = String.format("%064x", new BigInteger(1, digest.digest()));

		return toReturn;
	}

	public String createJwt(String userName, String passWord) {
		Algorithm algorithm = Algorithm.HMAC256("secret");

		String jwt = JWT.create().withIssuer("DataApp").withSubject("login")
				.withExpiresAt(new Date(System.currentTimeMillis() + prop.getJwtExpiredTime()))
				.withIssuedAt(new Date(System.currentTimeMillis())).withClaim("userName", userName)
				.withClaim("passWord", passWord).sign(algorithm);

		return jwt;
	}

	public String varifyJwt(String token, String key) {
		Algorithm algorithm = Algorithm.HMAC256("secret");

		JWTVerifier verifier = JWT.require(algorithm).build();
		DecodedJWT jwt = verifier.verify(token);

		return jwt.getClaim(key).asString();

	}

	public void userPool(String userName, String password) {

		AwsConfig aws = daoAws.find(1);
		AWSCredentials credentials = new BasicAWSCredentials(aws.getAccesskey(), aws.getSecretKey());

		AWSCognitoIdentityProvider userPoolClient = AWSCognitoIdentityProviderClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(credentials)).withRegion(Regions.US_WEST_2).build();

		if (password != null) {
			SignUpRequest signUpRequest = new SignUpRequest().withUsername(userName).withPassword(password)
					.withClientId(aws.getClientid());
			userPoolClient.signUp(signUpRequest);

			AdminConfirmSignUpRequest adminConfirmSignUpRequest = new AdminConfirmSignUpRequest()
					.withUserPoolId(aws.getUserpoolid()).withUsername(userName);
			userPoolClient.adminConfirmSignUp(adminConfirmSignUpRequest);

		} else {

			AdminDeleteUserRequest delRequest = new AdminDeleteUserRequest().withUsername(userName)
					.withUserPoolId(aws.getUserpoolid());

			userPoolClient.adminDeleteUser(delRequest);

		}
	}
}