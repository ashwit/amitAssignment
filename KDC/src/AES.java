import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class AES 
{
	private static String ALGORITHM = "AES";
	
	public static byte[] cipher(String text, String key) 
			throws IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException, InvalidKeyException
	{
		Key aesKey = 
				new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), ALGORITHM);			
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, aesKey);
		byte[] cipheredText = cipher.doFinal(text.getBytes());
		return cipheredText;
	}	
	
	public static String decipher(byte[] text, String key) 
			throws IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException, InvalidKeyException
	{
	  	 Key aesKey = 
	  			 new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), ALGORITHM);
		 Cipher decipher = Cipher.getInstance(ALGORITHM);
		 decipher.init(Cipher.DECRYPT_MODE, aesKey);
    	 byte[] decipheredText = decipher.doFinal(text);
    	 return new String(decipheredText);
	}	
	
	public static void printOut(String text)
	{
		System.out.println(text);
	}
	
	public static void printOut(byte[] text)
	{
		System.out.println(new String(text));
	}
	
}