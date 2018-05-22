package com.ct.webDemo.util;

public class BeanGSNameUtil {  
    
	////
	public static String getGetterMethodName(String property) {  
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
     * @param javaType 
     * @return 
     */  
    public static String getGetterMethodName(String property, Boolean type) {  
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
      
}  