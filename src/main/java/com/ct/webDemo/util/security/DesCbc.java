package com.ct.webDemo.util.security;

import java.io.UnsupportedEncodingException;  
import java.security.*;  
import javax.crypto.Cipher;  
import javax.crypto.SecretKey;  
import javax.crypto.SecretKeyFactory;  
import javax.crypto.spec.DESKeySpec;   
import javax.crypto.spec.IvParameterSpec;  

  
public class DesCbc {  
  
//private static final String PASSWORD_CRYPT_KEY = XmlUtil.getConfig().getPasswdKey().substring(0,8);   
//private final static String DES = "DES";   
//private static final byte[] desKey;    
	
private static final String PASSWORD_CRYPT_KEY = "ucserver";
//解密数据     
	public static String decrypt(String message,String key) throws Exception {     
          
        byte[] bytesrc =convertHexString(message);        
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");         
        DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));        
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");        
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);        
        IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));     
        cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);           
        byte[] retByte = cipher.doFinal(bytesrc);          
        return new String(retByte);      
	}    
	
	public static byte[] encrypt(String message, String key)     
        throws Exception {     
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");    
		DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));    
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");     
		SecretKey secretKey = keyFactory.generateSecret(desKeySpec);     
		IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));     
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);    
		return cipher.doFinal(message.getBytes("UTF-8"));     
	}    
	
	public static String encrypt(String value){  
		String result="";  
		try{  
			value=java.net.URLEncoder.encode(value, "utf-8");   
			result=toHexString(encrypt(value, PASSWORD_CRYPT_KEY)).toUpperCase();  
		}catch(Exception ex){  
		   ex.printStackTrace();  
		   return "";  
		}  
		return result;   
	}  
	public static byte[] convertHexString(String ss)      
	{      
		byte digest[] = new byte[ss.length() / 2];      
		for(int i = 0; i < digest.length; i++)      
		{      
			String byteString = ss.substring(2 * i, 2 * i + 2);      
			int byteValue = Integer.parseInt(byteString, 16);      
			digest[i] = (byte)byteValue;      
		}      
		return digest;      
	}     
	public static String toHexString(byte b[]) {     
	    StringBuffer hexString = new StringBuffer();     
	    for (int i = 0; i < b.length; i++) {     
	        String plainText = Integer.toHexString(0xff & b[i]);     
	        if (plainText.length() < 2)     
	            plainText = "0" + plainText;     
	        hexString.append(plainText);     
	    }        
	    return hexString.toString();     
	}     
	public static void main(String[] args) throws Exception {     
	    String value="[{\"user\":\"402892ee59b6e6930159b6e849740000\",\"mobile\":\"18205189527\"}]";     
	    System.out.println("加密数据:"+value);   
	    String a=encrypt( value);     
	    System.out.println("加密后的数据为:"+a);     
	}     
}  