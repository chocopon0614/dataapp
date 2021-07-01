package dataapp.util;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class hashutil {

	public static String getsha256(String input) throws NoSuchAlgorithmException, UnsupportedEncodingException {

		String toReturn = null;
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		digest.reset();
		digest.update(input.getBytes("utf8"));
		toReturn = String.format("%064x", new BigInteger(1, digest.digest()));

		return toReturn;
	}

}
