package com.ct.webDemo.util.security;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class AESEcbBase64 {

 
	public static void main(String[] args) throws Exception {
		String content = "[{\"syr\":\"李晓明\",\"hpzl\":\"小型\",\"hphm\":\"川AA678C\",\"syxz\":\"非营运\",\"ccdjrq\":\"2010-10-11\",\"zt\":\"正常\",\"yxqz\":\"2020-10-11\"}]";;  
	    String password = "jgdzzzsecritykey";  
	    // 加密  
	    System.out.println("加密前：" + content);  
	    String s = encrypt(content, password);  
	    System.out.println("加密后："+s);  
	    // 解密   
	    String s1 = decrypt(s, password);  
	    System.out.println("解密后：" +s1);  
	      
	}  

	public static String encrypt(String content, String password) {  
	    try {  
	    	SecretKey secretKey = getKey(password);  
	        byte[] enCodeFormat = secretKey.getEncoded();  
	        SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES"); 
	        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
	        cipher.init(Cipher.ENCRYPT_MODE, key);
	        byte[] result = cipher.doFinal(content.getBytes("utf-8"));  
	        String aft_aes = Base64.getEncoder().encodeToString(result).replaceAll("[\\s*\t\n\r]", "");
	        return aft_aes; 
	    } catch (NoSuchAlgorithmException e) {  
	        e.printStackTrace();  
	    } catch (NoSuchPaddingException e) {  
	        e.printStackTrace();  
	    } catch (InvalidKeyException e) {  
	        e.printStackTrace();  
	    } catch (IllegalBlockSizeException e) {  
	        e.printStackTrace();  
	    } catch (BadPaddingException e) {  
	        e.printStackTrace();  
	    } catch (UnsupportedEncodingException e) {  
	        e.printStackTrace();  
	    }  
	    return null;  
	}  
	public static String decrypt(String aft_aes, String password) {  
	    try {  
	        byte[] content = Base64.getDecoder().decode(aft_aes.replaceAll("[\\s*\t\n\r]", ""));  
	        SecretKey secretKey = getKey(password);  
	        byte[] enCodeFormat = secretKey.getEncoded();  
	        SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");  
	        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");// 创建密码器  
	        cipher.init(Cipher.DECRYPT_MODE, key);// 初始化  
	        byte[] result = cipher.doFinal(content);  
	        String bef_aes = new String(result);  
	        return bef_aes; // 加密  
	    } catch (NoSuchAlgorithmException e) {  
	        e.printStackTrace();  
	    } catch (NoSuchPaddingException e) {  
	        e.printStackTrace();  
	    } catch (InvalidKeyException e) {  
	        e.printStackTrace();  
	    } catch (IllegalBlockSizeException e) {  
	        e.printStackTrace();  
	    } catch (BadPaddingException e) {  
	        e.printStackTrace();  
	    }  
	    return null;  
	}  

	
	public static SecretKey getKey(String strKey) {  
	    try {             
	        KeyGenerator generator = KeyGenerator.getInstance("AES");  
	        //指定SHA1PRNG防止linux下解码报错
	        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");  
	        secureRandom.setSeed(strKey.getBytes());  
	        generator.init(128,secureRandom);  
	        return generator.generateKey();  
	    } catch (Exception e) {  
	        throw new RuntimeException("初始化密钥出现异常");  
	    }  
	  }   
		
}