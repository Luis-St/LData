package net.luis.data.cryption;

import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Objects;

/**
 * Cryption helper class
 *
 * @author Luis-St
 */

public class Cryption {
	
	private static final Encrypter ENCRYPTER = new Encrypter();
	private static final Decrypter DECRYPTER = new Decrypter();
	
	/**
	 * @return The encrypter
	 */
	public static @NotNull Encrypter getEncrypter() {
		return ENCRYPTER;
	}
	
	/**
	 * @return The decrypter
	 */
	public static @NotNull Decrypter getDecrypter() {
		return DECRYPTER;
	}
	
	/**
	 *
	 * @author Luis-St
	 *
	 */
	public static class Decrypter {
		
		@ApiStatus.Internal
		private Decrypter() {
			super();
		}
		
		/**
		 * Decrypts the given string
		 * @param input The string to decrypt
		 * @return The decrypted string
		 * @throws RuntimeException If an error occurs while decrypting
		 * @throws NullPointerException If the given string is null
		 * @see #decrypt(byte[])
		 */
		public @NotNull String decryptString(String input) {
			return new String(decrypt(Objects.requireNonNull(input, "Input must not be null").getBytes()));
		}
		
		/**
		 * Decrypts the given byte array into a string
		 * @param input The byte array to decrypt
		 * @return The decrypted string
		 * @throws RuntimeException If an error occurs while decrypting
		 * @see #decrypt(byte[])
		 */
		public @NotNull String decryptToString(byte[] input) {
			return new String(decrypt(input));
		}
		
		/**
		 * Decrypts the given byte array
		 * @param input The byte array to decrypt
		 * @return The decrypted byte array
		 * @throws RuntimeException If an error occurs while decrypting
		 */
		public byte[] decrypt(byte[] input) {
			try {
				byte[] decoded = Base64.getDecoder().decode(ArrayUtils.nullToEmpty(input));
				SecretKey secretKey = new SecretKeySpec(ArrayUtils.subarray(decoded, decoded.length - 32, decoded.length), "AES");
				Cipher cipher = Cipher.getInstance("AES");
				cipher.init(Cipher.DECRYPT_MODE, secretKey);
				return cipher.doFinal(ArrayUtils.subarray(decoded, 0, decoded.length - 32));
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}
	
	/**
	 *
	 * @author Luis-St
	 *
	 */
	public static class Encrypter {
		
		@ApiStatus.Internal
		private Encrypter() {
			super();
		}
		
		/**
		 * Encrypts the given string
		 * @param input The string to encrypt
		 * @return The encrypted string
		 * @throws NullPointerException If the given string is null
		 * @throws RuntimeException If an error occurs while encrypting
		 * @see #encrypt(byte[])
		 */
		public @NotNull String encryptString(String input) {
			return new String(encrypt(Objects.requireNonNull(input, "Input must not be null").getBytes()));
		}
		
		/**
		 * Encrypts the given byte array into a string
		 * @param input The byte array to encrypt
		 * @return The encrypted string
		 * @throws RuntimeException If an error occurs while encrypting
		 * @see #encrypt(byte[])
		 */
		public @NotNull String encryptToString(byte[] input) {
			return new String(encrypt(input));
		}
		
		/**
		 * Encrypts the given byte array
		 * @param input The byte array to encrypt
		 * @return The encrypted byte array
		 * @throws RuntimeException If an error occurs while encrypting
		 */
		public byte[] encrypt(byte[] input) {
			try {
				KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
				keyGenerator.init(256);
				SecretKey secretKey = keyGenerator.generateKey();
				Cipher cipher = Cipher.getInstance("AES");
				cipher.init(Cipher.ENCRYPT_MODE, secretKey);
				return Base64.getEncoder().encode(ArrayUtils.addAll(cipher.doFinal(ArrayUtils.nullToEmpty(input)), secretKey.getEncoded()));
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}
}
