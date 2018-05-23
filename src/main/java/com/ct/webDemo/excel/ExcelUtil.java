package com.ct.webDemo.excel;


import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;  
  
/** 
 * Excel工具类 
 * 
 */  
public class ExcelUtil {  
    public static final String OFFICE_EXCEL_2003_POSTFIX = "xls";  
    public static final String OFFICE_EXCEL_2010_POSTFIX = "xlsx";  
    public static final String EMPTY = "";  
    public static final String POINT = ".";  
    public static SimpleDateFormat sdf =   new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
    
    /** 
     * 获得path的后缀名 
     * @param path 
     * @return 
     */  
    public static String getPostfix(String path){  
        if(path==null || EMPTY.equals(path.trim())){  
            return EMPTY;  
        }  
        if(path.contains(POINT)){  
            return path.substring(path.lastIndexOf(POINT)+1,path.length());  
        }  
        return EMPTY;  
    }  

    /** 
     * 获得单元格的值
     * excel2003的.xls对应是HSSFCell，07的.xlsx对应的则是XSSFCell，
     * 但是他们都继承于Cell，所以使用Cell就可以使用两种格式的excel导入
     * @param  
     * @return 
     */
    public  String getValue(Cell cell) {  
        String value = "";  
        if(null==cell){  
            return value;  
        }  
        switch (cell.getCellType()) {  
        	// 数值型  
	        case Cell.CELL_TYPE_NUMERIC:  
	            if (HSSFDateUtil.isCellDateFormatted(cell)) {  
	                // 如果是date类型则 ，获取该cell的date值  
	                Date date = HSSFDateUtil.getJavaDate(cell.getNumericCellValue());  
	                // SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	                value = sdf.format(date);
	            }else {// 纯数字  
	                BigDecimal big=new BigDecimal(cell.getNumericCellValue());  
	                value = big.toString();  
	                // 解决1234.0  去掉后面的.0  
	                if(null!=value&&!"".equals(value.trim())){  
	                     String[] item = value.split("[.]");  
	                     if(1<item.length&&"0".equals(item[1])){  
	                         value=item[0];  
	                     }  
	                }  
	            }  
	            break;  
	        // 字符串类型   
	        case Cell.CELL_TYPE_STRING:  
	            value = cell.getStringCellValue().toString();  
	            break;  
	        // 公式类型  
	        case Cell.CELL_TYPE_FORMULA:  
	        // 读公式计算值  
	            value = String.valueOf(cell.getNumericCellValue());  
	            if (value.equals("NaN")) {// 如果获取的数据值为非法值,则转换为获取字符串  
	                value = cell.getStringCellValue().toString();  
	            }  
	            break;  
	        // 布尔类型  
	        case Cell.CELL_TYPE_BOOLEAN:  
	            value = " "+ cell.getBooleanCellValue();  
	            break;  
	        // 空值  
	        case Cell.CELL_TYPE_BLANK:   
	            value = "";  
	            break;  
	        // 故障  
	        case Cell.CELL_TYPE_ERROR:   
	            value = "";  
	            break;  
	        default:  
	            value = cell.getStringCellValue().toString();  
        }  
        if("null".endsWith(value.trim())){  
        	value="";  
        }  
		return value;  
    }  
    
}


 
