package project;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import java.util.Base64;

public class AESUtils {
	 private static SecretKey loadKey() {
         String encodedKey = System.getenv("AES_KEY");
         byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
         SecretKeySpec secret=new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
         return secret;
     }
    public static String encryptOTPKey(String otpKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE,loadKey());
        byte[] encryptedData = cipher.doFinal(otpKey.getBytes());
        return Base64.getEncoder().encodeToString(encryptedData);
    }

    
    public static String decryptOTPKey(String encryptedOTPKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE,loadKey());
        byte[] decodedData = Base64.getDecoder().decode(encryptedOTPKey);
        byte[] decryptedData = cipher.doFinal(decodedData);
        return new String(decryptedData);
    }
    
 
       

   
}
