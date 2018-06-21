package com.ct.webDemo.excel;


import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.alibaba.fastjson.JSON;
import com.ct.webDemo.base.ApplicationContextHelper;
import com.ct.webDemo.busi.service.DemoService;
import com.ct.webDemo.util.BeanGSNameUtil;  
  
/** 
 * Excel工具类 
 * 
 */  
@SuppressWarnings("all")
public class ExcelReaderUtil {  
    public static final String OFFICE_EXCEL_2003_POSTFIX = "xls";  
    public static final String OFFICE_EXCEL_2010_POSTFIX = "xlsx"; 
    public static final String XML_FILE_POSTFIX = "xml"; 
    
    
    private static String PACKAGE_PREFIX;
    private static String EXCEL_FILE_PATH;
    private static String XML_FILE_PATH;
    
    //private static String PACKAGE_PREFIX= "com.ct.webDemo.common.entity.";
    //private static String EXCEL_FILE_PATH = "D:/Test docs/loadInDBDir/";
    //private static String XML_FILE_PATH = "src/main/resources/excelXml/";
    
    public static final String EMPTY = "";  
    public static final String POINT = ".";  
    
    private String excelName ;
    private String xmlName ;
    private String entityCode ;
    
    public static SimpleDateFormat sdf =   new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
    
    private static DemoService demoService = ApplicationContextHelper.getBean(DemoService.class);
    private static final Logger logger = LoggerFactory.getLogger(ExcelReaderUtil.class);
     

    public ExcelReaderUtil (String excelName,String xmlName) {
    	this.excelName = excelName;
    	this.xmlName = xmlName;
    }
    
    public ExcelReaderUtil (String entityCode) {
    	this.excelName = entityCode + POINT + OFFICE_EXCEL_2010_POSTFIX;
    	this.xmlName = entityCode + POINT + XML_FILE_POSTFIX;
    }
    
    public void readExcel() {
	    String excelWholeName = this.EXCEL_FILE_PATH+this.excelName;
	    String xmlWholeName = this.XML_FILE_PATH + this.xmlName;
	    try {
	    	readExcel(excelWholeName,xmlWholeName);
	    }catch (Exception e) {
	    	e.printStackTrace();
	    }
    }
    
    public static void readExcel(String excelWholeName,String xmlWholeName) throws Exception {
        int totalRows =0;
        if (excelWholeName.endsWith(OFFICE_EXCEL_2003_POSTFIX)) { //处理excel2003文件
        	
        } else if (excelWholeName.endsWith(OFFICE_EXCEL_2010_POSTFIX)) {//处理excel2007文件
        	try {
	            ExcelXLSXReader excelXlsxReader = new ExcelXLSXReader(xmlWholeName,
	            		ExcelHandleConstans.PARSE_EXCEL_BY_XML,
	            		ExcelHandleConstans.PARSE_EXCEL_USE_MUTI_THREAD,
	            		ExcelHandleConstans.PARSE_EXCEL_USE_OUTTER_THREAD_POOL);
	            totalRows = excelXlsxReader.process(excelWholeName,1);
        	} catch (SAXParseException e) {
        		logger.error("Error ("+e.getLineNumber()+","   +e.getColumnNumber()+") : "+e.getMessage());  
        	} catch (SAXException e) {  
        		logger.error(e.getMessage());  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        } else {
            throw new Exception("文件格式错误，fileName的扩展名只能是xls或xlsx。");
        }
        logger.info("发送的总行数：" + totalRows);
    }
    
    /**
     * 将从Excel中读取的单元格数据，根据类名，转化为插入数据库的Bean List
     * @param dataList：单元格
     * @param rowCodeList：列名（bean的属性名）
     * @param entityCode：bean名
     * 
     * */
    public static List<Object> insertDataToDB(List<List<String>> dataList,List<String> rowCodeList,String entityCode) 
    	throws Exception{
		List<Object> insertList = new ArrayList<Object>();
		StringBuffer json = new StringBuffer("");
		String className = PACKAGE_PREFIX + BeanGSNameUtil.firstCharacterToUpper(entityCode);
		for (int i=0;i<dataList.size();i++) {
			List<String> data = dataList.get(i);
			if (data.size() == rowCodeList.size()) {
				json.append("{");
				for(int j=0 ;j<data.size(); j++) {
					//此处需检查时间类型格式是否符合
					json.append("'" + rowCodeList.get(j) + "':'" + data.get(j) + "'");
					if(j != data.size()-1) {
						json.append(",");
					}
				}
				json.append("}");
			}
			try {
				insertList.add(JSON.parseObject(json.toString(), Class.forName(className)));
				json.setLength(0);
			}catch (Exception e) {
				throw e;
			}
		}
		return insertList;
	}
    
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
    
    public String getExcelName() {
		return excelName;
	}
	public void setExcelName(String excelName) {
		this.excelName = excelName;
	}
	public String getXmlName() {
		return xmlName;
	}
	public void setXmlName(String xmlName) {
		this.xmlName = xmlName;
	}
    
	
	@Value("${entityPackagePrefix}")
	public void setPACKAGE_PREFIX(String pACKAGE_PREFIX) {
		ExcelReaderUtil.PACKAGE_PREFIX = pACKAGE_PREFIX;
		logger.info("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" +ExcelReaderUtil.PACKAGE_PREFIX);
	}
	@Value("${loadInDBDir}")
	public void setEXCEL_FILE_PATH(String eXCEL_FILE_PATH) {
		ExcelReaderUtil.EXCEL_FILE_PATH = eXCEL_FILE_PATH;
	}
	@Value("${excelXmlDir}")
	public void setXML_FILE_PATH(String xML_FILE_PATH) {
		ExcelReaderUtil.XML_FILE_PATH = xML_FILE_PATH;
	}

	
    public static void main(String[] args) throws Exception {
        String excelPath="D:/data.xlsx";
        String xmlPath = "src/main/resources/excelXml/product.xml";
        ExcelReaderUtil.readExcel(excelPath,xmlPath);
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


 
