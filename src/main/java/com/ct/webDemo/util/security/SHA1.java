package com.ct.webDemo.util.security;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.security.MessageDigest;  
/**
 *
 */
public  class SHA1 {
    private static final char[] HEX = {'0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static ByteBuffer buffer = ByteBuffer.allocate(8);   
    
    //private static 
    
    private static String getFormattedText(byte[] bytes) {
    	System.out.println(bytes.toString()); 
    	System.out.println("2进制："   + binary(bytes, 2)); 
    	System.out.println("2进制长度："   + binary(bytes, 2).length()); 
    	System.out.println("16进制："  + binary(bytes, 16));  
        System.out.println("32进制："  + binary(bytes, 32));  
        System.out.println("64进制："  + binary(bytes, 64));
        
        int len = bytes.length;
        StringBuilder buf = new StringBuilder(len * 2);
        
        // 把密文转换成十六进制的字符串形式  
        for (int j = 0; j < len; j++) {
            buf.append(HEX[(bytes[j] >> 4) & 0x0f]);
            buf.append(HEX[bytes[j] & 0x0f]);
        }
        return buf.toString();
    }
    public static String SHA1Encode(String str) {
        if (str == null) {
            return null;
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
            messageDigest.update(str.getBytes());
            return getFormattedText(messageDigest.digest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    

    /** 
     * 将byte[]转为各种进制的字符串 
     * @param bytes byte[] 
     * @param radix 基数可以转换进制的范围，从Character.MIN_RADIX到Character.MAX_RADIX，超出范围后变为10进制 
     * @return 转换后的字符串 
     */  
    public static String binary(byte[] bytes, int radix){  
        return new BigInteger(1, bytes).toString(radix);// 这里的1代表正数  
    }
    
    /**
     * 改进的32位FNV算法1
     * @param data 字符串
     * @return int值
     */
    public static int FNVHash1(String data)
    {
        final int p = 16777619;
        int hash = (int)2166136261L;
        for(int i=0;i<data.length();i++)
            hash = (hash ^ data.charAt(i)) * p;
        hash += hash << 13;
        hash ^= hash >> 7;
        hash += hash << 3;
        hash ^= hash >> 17;
        hash += hash << 5;
        System.out.println("FNVHash1：" + hash);  
        return hash;
    }
    
    public static long FNVHash1A64(String data) {
    	
    	final long FNV_64_INIT = 0xcbf29ce484222325L; 
    	final long FNV_64_PRIME = 0x100000001b3L;  
    	long rv = 0;  
		rv = FNV_64_INIT;  
		int len = data.length();  
		for (int i = 0; i < len; i++) {  
			 rv ^= data.charAt(i);  
             rv *= FNV_64_PRIME;  
		}  
		//return rv & 0xffffffffL; //返回32位hash
		return rv;
    }
    /**
     * 混合hash算法，输出64位的值
     */
    public static long mixHash(String str)
    {
        long hash = str.hashCode();
        hash <<= 32;
        hash |= FNVHash1(str);
        System.out.println("mixHash：" + hash); 
        return hash;
    }
    
    public static byte[] longToBytes(long x) {  
        buffer.putLong(0, x);  
        return buffer.array();  
    }  
    
    public static byte intToByte(int x) { 
        return (byte) x;  
    }  
    
    public static byte[] intToByteArray(int a) {   
    	return new byte[] {   
    	        (byte) ((a >> 24) & 0xFF),   
    	        (byte) ((a >> 16) & 0xFF),      
    	        (byte) ((a >> 8) & 0xFF),      
    	        (byte) (a & 0xFF)   
    	    };   
    }
    
    public static String toBinary(String str){
        //把字符串转成字符数组
        char[] strChar=str.toCharArray();
        String result="";
        for(int i=0;i<strChar.length;i++){
            //toBinaryString(int i)返回变量的二进制表示的字符串
            //toHexString(int i) 八进制
            //toOctalString(int i) 十六进制
            result +=Integer.toBinaryString(strChar[i])+ " ";
        }
        return result;
    }

    
    public static void main(String[] args) throws Exception {
		String content = "7492379319591759798"; 
		
		System.out.println(toBinary(content));  
		getFormattedText(content.getBytes());
	    // 加密  
	    System.out.println("加密前：" + content);  
	    String s = SHA1Encode(content);  
	    System.out.println("加密后："+s);  
	    
	    long l = mixHash(content);
	    getFormattedText(longToBytes(l)); 
	    
	    l= FNVHash1A64(content);
	    System.out.println("长整型："+l); 
	    getFormattedText(longToBytes(l)); 
	    int i = FNVHash1(content);
	    String binaryStr = java.lang.Integer.toBinaryString(i);
		System.out.println("int二进制字符串： " + binaryStr);
		
		getFormattedText(intToByteArray(i)); 
	}  
    
    
}  