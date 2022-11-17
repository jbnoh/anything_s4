package com.anything.utils;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AES256Util {

	private final static String ALG = "AES/CBC/PKCS5Padding";

	private final static String KEY = "A1qs2WD3ef4RG5th6Y";

	private final static String IV = KEY.substring(0, 16);

	public static String encrypt(String text) throws Exception {

		SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes(), "AES");
		IvParameterSpec ivParamSpec = new IvParameterSpec(IV.getBytes());

		Cipher cipher = Cipher.getInstance(ALG);
		cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParamSpec);

		byte[] encrypted = cipher.doFinal(text.getBytes("UTF-8"));
		return Base64.getEncoder().encodeToString(encrypted);
	}

	public static String decrypt(String cipherText) throws Exception {

		SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes(), "AES");
		IvParameterSpec ivParamSpec = new IvParameterSpec(IV.getBytes());

		Cipher cipher = Cipher.getInstance(ALG);
		cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParamSpec);

		byte[] decodedBytes = Base64.getDecoder().decode(cipherText);
		byte[] decrypted = cipher.doFinal(decodedBytes);
		return new String(decrypted, "UTF-8");
	}
}
