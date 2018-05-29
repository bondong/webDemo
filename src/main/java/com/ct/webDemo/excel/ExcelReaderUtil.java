package com.ct.webDemo.excel;


import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;  
  
/** 
 * Excel工具类 
 * 
 */  
public class ExcelReaderUtil {  
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
    
    /**
     * 每获取一条记录，即打印
     * 在flume里每获取一条记录即发送，而不必缓存起来，可以大大减少内存的消耗，这里主要是针对flume读取大数据量excel来说的
     * @param sheetName
     * @param sheetIndex
     * @param curRow
     * @param cellList
     */
    public static void sendRows(String filePath, String sheetName, int sheetIndex, int curRow, List<String> cellList) {
            StringBuffer oneLineSb = new StringBuffer();
            oneLineSb.append(filePath);
            oneLineSb.append("--");
            oneLineSb.append("sheet" + sheetIndex);
            oneLineSb.append("::" + sheetName);//加上sheet名
            oneLineSb.append("--");
            oneLineSb.append("row" + curRow);
            oneLineSb.append("::");
            for (String cell : cellList) {
                oneLineSb.append(cell.trim());
                oneLineSb.append("|");
            }
            String oneLine = oneLineSb.toString();
            if (oneLine.endsWith("|")) {
                oneLine = oneLine.substring(0, oneLine.lastIndexOf("|"));
            }// 去除最后一个分隔符

            System.out.println(oneLine);
    }
    
    public static void readExcel(String fileName,String xmlPath) throws Exception {
        int totalRows =0;
        if (fileName.endsWith(OFFICE_EXCEL_2003_POSTFIX)) { //处理excel2003文件
        	
        } else if (fileName.endsWith(OFFICE_EXCEL_2010_POSTFIX)) {//处理excel2007文件
        	try {
	            ExcelXLSXReader excelXlsxReader = new ExcelXLSXReader(xmlPath,ExcelHandleConstans.PARSE_EXCEL_BY_XML);
	            totalRows = excelXlsxReader.process(fileName);
        	} catch (SAXParseException e) {
        		System.out.println("Error ("+e.getLineNumber()+","   +e.getColumnNumber()+") : "+e.getMessage());  
        	} catch (SAXException e) {  
                System.out.println(e.getMessage());  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        } else {
            throw new Exception("文件格式错误，fileName的扩展名只能是xls或xlsx。");
        }
        System.out.println("发送的总行数：" + totalRows);
    }

    public static void main(String[] args) throws Exception {
        String excelPath="D:/data.xlsx";
        String xmlPath = "src/main/resources/excelXml/product.xml";
        ExcelReaderUtil.readExcel(excelPath,xmlPath);
    }
}


 
