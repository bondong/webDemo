package com.ct.webDemo.util.security;


/**
 * 加密解密工具类
 */
public class EncryUtil {
	
	/**
	 * 使用默认密钥进行DES加密
	 */
	public static String encrypt(String plainText) {
		try {
			return new Des().encrypt(plainText);
		} catch (Exception e) {
			return null;
		}
	}
 
	
	/**
	 * 使用指定密钥进行DES解密
	 */
	public static String encrypt(String plainText, String key) {
		try {
			return new Des(key).encrypt(plainText);
		} catch (Exception e) {
			return null;
		}
	}
	
 
	/**
	 * 使用默认密钥进行DES解密
	 */
	public static String decrypt(String plainText) {
		try {
			return new Des().decrypt(plainText);
		} catch (Exception e) {
			return null;
		}
	}
 
	
	/**
	 * 使用指定密钥进行DES解密
	 */
	public static String decrypt(String plainText, String key) {
		try {
			return new Des(key).decrypt(plainText);
		} catch (Exception e) {
			return null;
		}
	}
	
	
	public static void main(String[] args) throws Exception {
		String str = "[{'goods_id':'18','cat_id':'2','goods_sn':'FDS000018','goods_name':'test'}]";
		String t = "";
		System.out.println("加密后：" + (t = EncryUtil.encrypt(str)));
		System.out.println("解密后：" + EncryUtil.decrypt(t));
	}
 
}

