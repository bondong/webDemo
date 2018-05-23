package com.ct.webDemo.util;

public class BeanGSNameUtil {  
    
	
	/** 
     * 未考虑boolean的情况,获取对应的getter方法名 
     * @param property 
     * @return 
     */
	public static String getGetterMethodName(String property) {  
		
		//寻找下划线并替换后个字母为大写
		property = replaceUnderlineAndfirstToUpper(property, "_", "");
		
        StringBuilder sb = new StringBuilder();  
        sb.append(property);  
        
        if (Character.isLowerCase(sb.charAt(0))) {  
            if (sb.length() == 1 || !Character.isUpperCase(sb.charAt(1))) {  
                sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));  
            }  
        }  
        sb.insert(0, "get");   
        return sb.toString();  
    }  
    /** 
     * 根据属性名称和java类型，获取对应的getter方法名 
     * @param property 
     * @param type  如果是boolean类型，方法是 is 开头
     * @return 
     */  
    public static String getGetterMethodName(String property, Boolean type) {  
    	
    	property = replaceUnderlineAndfirstToUpper(property, "_", "");
        StringBuilder sb = new StringBuilder();  
        sb.append(property);  
        if (Character.isLowerCase(sb.charAt(0))) {  
            if (sb.length() == 1 || !Character.isUpperCase(sb.charAt(1))) {  
                sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));  
            }  
        }  
        if (type) {  
            sb.insert(0, "is");   
        } else {  
            sb.insert(0, "get");   
        }  
        return sb.toString();  
    }  
    /** 
     * 根据属性名称获取对应的setter方法名称 
     * @param property 
     * @return 
     */  
    public static String getSetterMethodName(String property) {  
        StringBuilder sb = new StringBuilder();  
        sb.append(property);  
        if (Character.isLowerCase(sb.charAt(0))) {  
            if (sb.length() == 1 || !Character.isUpperCase(sb.charAt(1))) {  
                sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));  
            }  
        }  
        sb.insert(0, "set");   
        return sb.toString();  
    }  
      
    
    /** 
     * 替换字符串并让它的下一个字母为大写 
     *  
     * @param srcStr 
     * @param org 
     * @param ob 
     * @return 
     */  
    public static String replaceUnderlineAndfirstToUpper(String srcStr, String org, String ob) {  
        String newString = "";  
        int first = 0;  
        while (srcStr.indexOf(org) != -1) {  
            first = srcStr.indexOf(org);  
            if (first != srcStr.length()) {  
                newString = newString + srcStr.substring(0, first) + ob;  
                srcStr = srcStr.substring(first + org.length(), srcStr.length());  
                srcStr = firstCharacterToUpper(srcStr);  
            }  
        }  
        newString = newString + srcStr;  
        return newString;  
    } 
    
    /** 
     * 首字母大写 
     *  
     * @param srcStr 
     * @return 
     */  
    public static String firstCharacterToUpper(String srcStr) {  
        return srcStr.substring(0, 1).toUpperCase() + srcStr.substring(1);  
    }  
    
    public static void main(String[] args) {  
    	System.out.println(getGetterMethodName("ni_hao_abc"));
        System.out.println(replaceUnderlineAndfirstToUpper("ni_hao_abc", "_", ""));  
    }  
}  