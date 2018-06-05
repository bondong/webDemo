package com.ct.webDemo.excel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * EXCEL文件导入时，校验单元格是否为合法格式的方法集合
 * 
 */

public class ExcelCellValidate{
	
	
	public static boolean validate(String rule,String value) {
		boolean result = true;
		
		if(ExcelHandleConstans.RULE_NAME_NULLABLE.equals(rule)){
			if (null == value || "".equals(value))
				result = false;
		}else if (ExcelHandleConstans.RULE_DATE_FORMAT.equals(rule)) {
			
		}else if (ExcelHandleConstans.RULE_NUMBER_FORMAT.equals(rule)) {
			if (null != value || !"".equals(value)) {
				result = isNumeric(value);
			}
		}else if (ExcelHandleConstans.RULE_NAME_UNIQUE.equals(rule)) {
			
		}
		
		return result;
	}
	
	/**
     * 利用正则表达式判断字符串是否是整数
     * @param str
     * @return
     */
    public static boolean isInt(String str){
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if( !isNum.matches() ){
			return false;
		}
		return true;
    }
    
    /**
     * 利用正则表达式判断字符串是否是数字 ：正负，小数
     * @param str
     * @return
     */
    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("^(-?\\d+)(\\.\\d+)?$");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
	}
    
    /**
     * 功能：判断字符串是否为日期格式,CST格式除外,
     * 
     * @param str
     * @return
     */
    public static boolean isDate(String strDate) {
        Pattern pattern = Pattern
                .compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
        Matcher m = pattern.matcher(strDate);
        if (m.matches()) {
            return true;
        } else {
            return false;
        }
    }
    
    public static void main(String[] args) throws Exception {
    	
    	System.out.println(isNumeric("-4.53"));
    	System.out.println(isNumeric("18"));
    	System.out.println(isNumeric("18.56"));
    	System.out.println(isNumeric("4.12x"));
    	System.out.println(isInt("56"));
    	System.out.println(isInt("056"));
    	System.out.println(isInt("x"));
    }
}