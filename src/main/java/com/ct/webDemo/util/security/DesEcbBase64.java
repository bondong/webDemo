package com.ct.webDemo.util.security;

import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
/**
 * 3DES加密工具类
 */
public class DesEcbBase64 {
    // 密钥(DES加密和解密过程中，密钥长度都必须是8的倍数)
    private final static String secretKey = "dzzzskey";

    // 加解密统一使用的编码方式
    private final static String encoding = "utf-8";

    /**
     * 3DES加密
     *
     * @param plainText 普通文本
     * @return
     * @throws Exception
     */
    public static String encode(String plainText) throws Exception {
        // DES算法要求有一个可信任的随机数源，指定SHA1PRNG防止linux下解码报错
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG" );  
        // 从原始密钥数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(secretKey.getBytes());
        // 创建一个密匙工厂，然后用它把DESKeySpec转换成一个SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");//DES加密和解密过程中，密钥长度都必须是8的倍数
        SecretKey secretKey = keyFactory.generateSecret(dks);
        // 其他语种需指定ECB模式
        Cipher cipher = Cipher.getInstance("DES/ECB/pkcs5padding");
        // 用密匙初始化Cipher对象
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, sr);
        // 执行加密操作
        byte[] encryptData = cipher.doFinal(plainText.getBytes(encoding));
        return Base64.getEncoder().encodeToString(encryptData);
        //return toHexString(encryptData);  //16进制
    }

    /**
     * 3DES解密
     *
     * @param encryptText 加密文本
     * @return
     * @throws Exception
     */
    public static String decode(String encryptText) throws Exception {
        // DES算法要求有一个可信任的随机数源，指定SHA1PRNG防止linux下解码报错
    	SecureRandom sr = SecureRandom.getInstance("SHA1PRNG" );  
        // 从原始密匙数据创建一个DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(secretKey.getBytes());
        // 创建一个密匙工厂，然后用它把DESKeySpec对象转换成一个SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(dks);
        // 其他语种需指定ECB模式
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        // 用密匙初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, secretKey, sr);
        // 正式执行解密操作
        byte[] decryptData = cipher.doFinal(Base64.getDecoder().decode(encryptText));
        //byte[] decryptData = cipher.doFinal(convertHexString(encryptText));  //16进制
        return new String(decryptData, encoding);
    }
    
    public static void main(String[] args) throws Exception {
        //String plainText = "{\"expireTime\":\"1262304805000\",\"licenceInfo\":[{\"syr\":\"李晓明\",\"hpzl\":\"小型\",\"hphm\":\"川AA678C\",\"syxz\":\"非营运\",\"ccdjrq\":\"2010-10-11\",\"zt\":\"正常\",\"yxqz\":\"2020-10-11\"}]}";
        String plainText = "[{\"syr\":\"李晓明\",\"hpzl\":\"小型\",\"hphm\":\"川AA678C\",\"syxz\":\"非营运\",\"ccdjrq\":\"2010-10-11\",\"zt\":\"正常\",\"yxqz\":\"2020-10-11\"}]";
        
        //String encryptText = DesEcbBase64.encode(plainText).replace(" ", "");//base64去空格
        String encryptText = DesEcbBase64.encode(plainText);
        System.out.println(encryptText);
        System.out.println("密文长度："+encryptText.length());
        System.out.println(DesEcbBase64.decode(encryptText));
    }
    
    
	//十六进制转换
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
	//十六进制转换
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
}
