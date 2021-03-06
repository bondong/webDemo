package com.ct.webDemo.excel;

public class ExcelHandleConstans {
	
	/**xml中验证规则的名称**/
	//不为空
	public static String RULE_NAME_NULLABLE = "nullable";
	//唯一
	public static String RULE_NAME_UNIQUE = "checkUnique";
	//时间格式
	public static String RULE_DATE_FORMAT ="checkDateFormat";
	//纯数字
	public static String RULE_NUMBER_FORMAT ="checkNumber";
	
	//excel 使用XML校验模板标志
	public static boolean PARSE_EXCEL_BY_XML = true;
	public static boolean NO_PARSE_EXCEL_BY_XML = false;
	//使用多线程读取excel
	public static boolean PARSE_EXCEL_USE_MUTI_THREAD = true;
	public static boolean NO_PARSE_EXCEL_USE_MUTI_THREAD = false;
	//使用外部线程池读取excel
	public static boolean PARSE_EXCEL_USE_OUTTER_THREAD_POOL = true;
	public static boolean NO_PARSE_EXCEL_USE_OUTTER_THREAD_POOL = false;
	
	//excel 中的模板数据错误
	public static String ERROR_EXCEL_NULL="excel中数据为空";
	public static String ERROR_EXCEL_COLUMN_NOT_EQUAL="xml列与excel列不一致";
	public static String ERROR_EXCEL_DATA_TYPE = "数据类型错误";
	public static String ERROR_DB_OPER_IN_THREAD = "操作数据库异常";
	public static String ERROR_DB_OPER_IN_THREAD_TIMEOUT = "操作数据库超时，中断线程";

}
