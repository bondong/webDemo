package com.ct.webDemo.excel;


import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import com.alibaba.fastjson.JSON;
import com.ct.webDemo.threadPool.AutomicCounter;
import com.ct.webDemo.threadPool.ThreadPoolManager;
import com.ct.webDemo.util.BeanGSNameUtil;
import com.ct.webDemo.util.ParseXMLUtil;

/**
 * @author 
 * POI读取excel有两种模式，一种是用户模式，一种是事件驱动模式
 * 采用SAX事件驱动模式解决XLSX文件，可以有效解决用户模式内存溢出的问题，
 * 该模式是POI官方推荐的读取大数据的模式，
 * 在用户模式下，数据量较大，Sheet较多，或者是有很多无用的空行的情况下，容易出现内存溢出
 * 
 * 用于解决.xlsx2007版本大数据量问题
 **/
@SuppressWarnings("all")
public class ExcelXLSXReader extends DefaultHandler  {
	
	private static final Logger logger = LoggerFactory.getLogger(ExcelXLSXReader.class);
	
    //单元格中的数据可能的数据类型
    enum CellDataType {
        BOOL, ERROR, FORMULA, INLINESTR, SSTINDEX, NUMBER, DATE, NULL
    }
	// 共享字符串表
    private SharedStringsTable sst;
    //上一次的索引值
    private String lastIndex;
    //文件的绝对路径
    private String filePath = "";
    //工作表索引
    private int sheetIndex = 0;
    //sheet名
    private String sheetName = "";
    //总行数
    private int totalRows=0;
    //一行内cell集合
    private List<String> cellList = new ArrayList<String>();
    //判断整行是否为空行的标记
    private boolean flag = false;
    //当前行
    private int curRow = 1;
    //当前列
    private int curCol = 0;
    //T元素标识
    private boolean isTElement;
    //异常信息，如果为空则表示没有异常
    private String exceptionMessage;
    //文档读取结束标志
    private boolean docEndFlag = false;
    //单元格数据类型，默认为字符串类型
    private CellDataType nextDataType = CellDataType.SSTINDEX;

    private final DataFormatter formatter = new DataFormatter();
    //单元格日期格式的索引
    private short formatIndex;
    //日期格式字符串
    private String formatString;
    //定义前一个元素和当前元素的位置，用来计算其中空的单元格数量，如A6和A8等
    private String preRef = null, ref = null;
    //定义该文档一行最大的单元格数，用来补全一行最后可能缺失的单元格
    private String maxRef = null;
    //单元格
    private StylesTable stylesTable;
    //解析xml文件对象
    private ParseXMLUtil parseXMLUtil = null;
    //xml校验标识
    private boolean validateByXMLFlag = false;
    //xml配置的对象代码
    String entityCode = null;
    //表头信息
    private List<String> rowNameList = new ArrayList<String>();
    private List<String> rowCodeList = new ArrayList<String>();
    
    //缓存的数据集
    public List<List<String>> dataList = new ArrayList<List<String>>();
    
    //线程池  
    //多线程开启标志
    private boolean mutiThreadFlag = false;
    private ExecutorService fixedThreadPool = null;
    private boolean outterThreadPoolFlag = false;
    
    /**
     * 
     * @param xmlPath
     * @param validateByXMLFlag 是否开启XML校验
     * @param mutiThreadFlag 是否采用多线程读取。但此处逻辑有瑕疵：即使标记为不采用多线程，实际也采用了一个线程的线程池
     * @param outterThreadPoolFlag 外部线程池标记，如使用内部线程池，则读取结束后续关闭
     * 
     */
    public ExcelXLSXReader(String xmlPath,boolean validateByXMLFlag,boolean mutiThreadFlag,boolean outterThreadPoolFlag) {
    	this.mutiThreadFlag = mutiThreadFlag;
    	this.outterThreadPoolFlag = outterThreadPoolFlag;
    	if (null!=xmlPath && !"".equals(xmlPath)) {
    		File file = new File(xmlPath);
    		this.parseXMLUtil = new ParseXMLUtil(file);
    		this.validateByXMLFlag = validateByXMLFlag;
    	}
    	int threadno=mutiThreadFlag?Runtime.getRuntime().availableProcessors():1;  
    	if (mutiThreadFlag) {
    		fixedThreadPool = ThreadPoolManager.getThreadPoolExecutor();
        }else
        	fixedThreadPool=Executors.newFixedThreadPool(threadno);
    }
    public ExcelXLSXReader() {}
    /**
     * 遍历工作簿中所有的电子表格
     * 并缓存在mySheetList中
     *
     * @param filename
     * @throws Exception
     */
    public int process(String filename) throws Exception {
        filePath = filename;
        OPCPackage pkg = OPCPackage.open(filename);
        XSSFReader xssfReader = new XSSFReader(pkg);
        stylesTable = xssfReader.getStylesTable();
        SharedStringsTable sst = xssfReader.getSharedStringsTable();
        XMLReader parser=fetchSheetParser(sst);  
        
       /* ParseXMLErrorHandler parseXMLErrorHandler = new ParseXMLErrorHandler();
        parser.setErrorHandler(parseXMLErrorHandler);
        ErrorHandler handler = parser.getErrorHandler();
        System.out.println("Error handler is currently: " + handler.getClass().getName());*/
        
        XSSFReader.SheetIterator sheets = (XSSFReader.SheetIterator) xssfReader.getSheetsData();
        while (sheets.hasNext()) { //遍历sheet
            curRow = 1; //标记初始行为第一行
            sheetIndex++;
            InputStream sheet = sheets.next(); //sheets.next()和sheets.getSheetName()不能换位置，否则sheetName报错
            sheetName = sheets.getSheetName();
            InputSource sheetSource = new InputSource(sheet);
            parser.parse(sheetSource); //解析excel的每条记录，在这个过程中startElement()、characters()、endElement()这三个函数会依次执行
            sheet.close();
        }
        pkg.close();
        return totalRows; //返回该excel文件的总行数，不包括首列和空行
    }
    
    /** 
     * 只读第一个电子表格，其中sheetId为要遍历的sheet索引，从1开始，1-3 ,bug未解决
     * @param filename 
     * @param sheetId 
     * @throws Exception 
     */  
    public int process(String filename, int sheetId) throws Exception {  
    	filePath = filename;
        OPCPackage pkg = OPCPackage.open(filename);  
        XSSFReader xssfReader = new XSSFReader(pkg);  
        stylesTable = xssfReader.getStylesTable();
        SharedStringsTable sst = xssfReader.getSharedStringsTable();  
        XMLReader parser = fetchSheetParser(sst);  
        // 根据 rId# 或 rSheet# 查找sheet，测试有错误，好像读到了sharedStrings.xml
        //InputStream sheet = xssfReader.getSheet("rId1"); 
        InputStream sheet = xssfReader.getSheetsData().next();
        InputSource sheetSource = new InputSource(sheet);  
        parser.parse(sheetSource);  
        sheet.close();  
        pkg.close();
        return totalRows;
    }  
    
    private XMLReader fetchSheetParser(SharedStringsTable sst)  
            throws SAXException {  
        XMLReader parser = XMLReaderFactory  
                .createXMLReader("org.apache.xerces.parsers.SAXParser");  
        this.sst = sst;  
        parser.setContentHandler(this);  
        return parser;  
    }  

    /**
     * 第一个执行
     *
     * @param uri
     * @param localName
     * @param name
     * @param attributes
     * @throws SAXException
     */
    @Override
    public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
        //c => 单元格
    	if ("c".equals(name)) {
            //前一个单元格的位置
            if (preRef == null) {//每行第一格
                //preRef = attributes.getValue("r"); //这里会造成第一列为空值不能取出
            } else {
                //preRef = ref;  //这里会造成中间连续空格丢失
            }

            //当前单元格的位置
            ref = attributes.getValue("r");
            //设定单元格类型
            this.setNextDataType(attributes);
        }

        //当元素为t时
        if ("t".equals(name)) {
            isTElement = true;
        } else {
            isTElement = false;
        }

        //置空
        lastIndex = "";
    }

    /**
     * 第二个执行
     * 得到单元格对应的索引值或是内容值
     * 如果单元格类型是字符串等，lastIndex则是索引值
     * 如果单元格类型是数字、日期、百分比、布尔值、错误、公式、INLINESTR，lastIndex则是内容值
     * @param ch
     * @param start
     * @param length
     * @throws SAXException
     */
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        lastIndex += new String(ch, start, length);
    }

    /**
     * 第三个执行
     *
     * @param uri
     * @param localName
     * @param name
     * @throws SAXException
     */
    @Override
    public void endElement(String uri, String localName, String name) throws SAXException {

        //t元素也包含字符串
        if (isTElement) {//这个程序没经过
            //将单元格内容加入rowlist中，在这之前先去掉字符串前后的空白符
            String value = lastIndex.trim();
            cellRuleValidate(curCol, value);
            curCol++;
            isTElement = false;
            //如果某个单元格含有值，则标识该行不为空行
            if (!flag && value != null && !"".equals(value.trim())) {
                flag = true;
            }
        } else if ("v".equals(name)) {
            //v => 单元格的值，如果单元格是字符串，则v标签的值为该字符串在SST中的索引，如果单元格为空，则没有v标签
            String value = this.getDataValue(lastIndex.trim(), "");//根据索引值获取对应的单元格值
            //补全单元格之间的空单元格,preRef可能为Null，表示当前为第一列，或者从第一列开始都为空值
            if (!ref.equals(preRef)) {
                int len = countNullCell(ref, preRef);
                for (int i = 0; i < len; i++) {
                	cellRuleValidate(curCol, "");
                    curCol++;
                }
            }
            cellRuleValidate(curCol, value);
            curCol++;
            //重要，执行完补空操作后，再把当前格赋值给前一格，再进入下一格判断
            //需考虑优化，每个格都会调用一次countNullCell方法
            preRef = ref;
            
            //如果里面某个单元格含有值，则标识该行不为空行
            if (value != null && !"".equals(value.trim())) {
                flag = true;
            }
            //throw new SAXParseException("测试SAXParseException",null,null,curRow,curCol);
        } else {
            //如果标签名称为row，这说明已到行尾，调用optRows()方法
            if ("row".equals(name)) {
                //默认第一行为表头，以该行单元格数目为最大数目
                if (curRow == 1) {
                    maxRef = ref;
                    rowNameList.addAll(cellList);
                    //如果有xml校验标志,先进性excel的表头校验,验证不通过throw new SAXException,中断读取excel
                    if (validateByXMLFlag && null != parseXMLUtil) {
                    	boolean rowNameErrorFlag = false;
                    	entityCode = parseXMLUtil.getEntityList().get(0);
                    	List<String> xmlRowNameList = parseXMLUtil.getExcelFieldsFromXml();
                    	
                    	if (rowNameList.size() != xmlRowNameList.size())  
                    		rowNameErrorFlag = true;  
                        for (Object object : rowNameList) {  
                            if (!xmlRowNameList.contains(object)) {  
                            	rowNameErrorFlag = true; 
                            	break;
                            }
                        }    
	                    if(rowNameErrorFlag) {
	                    	throw new SAXException(ExcelHandleConstans.ERROR_EXCEL_COLUMN_NOT_EQUAL);
	                    }else {
	                    	rowCodeList = parseXMLUtil.getBeanPropertiesFromXmlByEntity(entityCode);
	                    	System.out.println(Arrays.toString(rowCodeList.toArray()));
	                    }
                    }
                }
                //补全一行尾部可能缺失的单元格
                if (maxRef != null) {
                    int len = countNullCell(maxRef, ref);
                    for (int i = 0; i <= len; i++) {
                    	cellRuleValidate(curCol, "");
                        curCol++;
                    }
                }
                //该行不为空行且该行不是第一行，则进行数据处理（第一行为列名，不需要）
                if (flag&&curRow!=1){ 
                	try {
                		optRow(sheetIndex, curRow, cellList);  
                	}catch (Exception e) {
                		throw new SAXException(ExcelHandleConstans.ERROR_DB_OPER_IN_THREAD);
                	}
                    totalRows++;
                }
                
                //使用cellList.clear()会导致dataList数据丢失
                cellList = new ArrayList<String>();
                curRow++;
                curCol = 0;
                preRef = null;
                ref = null;
                flag=false;
            }else if ("sheetData".equals(name)) {
            	
            }
        }
    }
    
    /**
     * 最后执行
     *
     * @throws SAXException
     */
    @Override  
    public void endDocument() throws SAXException {  
        this.docEndFlag = true;
        try {
        	optRow(sheetIndex, curRow, cellList);
        }catch (Exception e) {
    		throw new SAXException(ExcelHandleConstans.ERROR_DB_OPER_IN_THREAD);
    	}
    	super.endDocument();  
    	
    	if(!outterThreadPoolFlag) {
	    	fixedThreadPool.shutdown();
	    	try {
	    		if (!fixedThreadPool.awaitTermination(10,TimeUnit.MINUTES)) {
	    			{
	    				fixedThreadPool.shutdownNow(); 
	    				//超时提醒
	    				throw new SAXException(ExcelHandleConstans.ERROR_DB_OPER_IN_THREAD_TIMEOUT);
	    			}
	    		}
	    	} catch (InterruptedException ie) {
	    		fixedThreadPool.shutdownNow();
	    		Thread.currentThread().interrupt();
	    	}
    	}
    } 
    
    /**
     * 处理数据类型
     *
     * @param attributes
     */
    public void setNextDataType(Attributes attributes) {
        nextDataType = CellDataType.NUMBER; //cellType为空，则表示该单元格类型为数字
        formatIndex = -1;
        formatString = null;
        String cellType = attributes.getValue("t"); //单元格类型
        String cellStyleStr = attributes.getValue("s"); //非字符串格式，日期或数字等有此属性，值为1,2,3,4,5,6,7.......
        String columnData = attributes.getValue("r"); //获取单元格的位置，如A1,B1

        if ("b".equals(cellType)) { //处理布尔值
            nextDataType = CellDataType.BOOL;
        } else if ("e".equals(cellType)) {  //处理错误
            nextDataType = CellDataType.ERROR;
        } else if ("inlineStr".equals(cellType)) {
            nextDataType = CellDataType.INLINESTR;
        } else if ("s".equals(cellType)) { //处理字符串
            nextDataType = CellDataType.SSTINDEX;
        } else if ("str".equals(cellType)) {
            nextDataType = CellDataType.FORMULA;
        }

        if (cellStyleStr != null) { //处理非字符串格式
            int styleIndex = Integer.parseInt(cellStyleStr);
            XSSFCellStyle style = stylesTable.getStyleAt(styleIndex);
            formatIndex = style.getDataFormat();
            formatString = style.getDataFormatString();
            //System.out.println(formatIndex + "  " + formatString);

            if (formatString.contains("m/d/yy")) {//如果是日期
                nextDataType = CellDataType.DATE;
                formatString = "yyyy-MM-dd hh:mm:ss";
            }

            if (formatString == null) {//非日期，即数字
                nextDataType = CellDataType.NULL;
                formatString = BuiltinFormats.getBuiltinFormat(formatIndex);
            }
        }
    }

    /**
     * 对解析出来的数据进行类型处理
     * @param value   单元格的值，
     *                value代表解析：BOOL的为0或1， ERROR的为内容值，FORMULA的为内容值，INLINESTR的为索引值需转换为内容值，
     *                SSTINDEX的为索引值需转换为内容值， NUMBER为内容值，DATE为内容值
     * @param thisStr 一个空字符串
     * @return
     */
    public String getDataValue(String value, String thisStr) {
        switch (nextDataType) {
            // 这几个的顺序不能随便交换，交换了很可能会导致数据错误
            case BOOL: //布尔值
                char first = value.charAt(0);
                thisStr = first == '0'? "FALSE" : "TRUE";
                break;
            case ERROR: //错误
                thisStr = "\"ERROR:" + value.toString() + '"';
                break;
            case FORMULA: //公式
                thisStr = '"' + value.toString() + '"';
                break;
            case INLINESTR:
                XSSFRichTextString rtsi = new XSSFRichTextString(value.toString());
                thisStr = rtsi.toString();
                rtsi = null;
                break;
            case SSTINDEX: //字符串
                String sstIndex = value.toString();
                try {
                    int idx = Integer.parseInt(sstIndex);
                    XSSFRichTextString rtss = new XSSFRichTextString(sst.getEntryAt(idx));//根据idx索引值获取内容值
                    thisStr = rtss.toString();
                    rtss = null;
                } catch (NumberFormatException ex) {
                    thisStr = value.toString();
                }
                break;
            case NUMBER: //数字
                if (formatString != null) {
                    thisStr = formatter.formatRawCellContents(Double.parseDouble(value), formatIndex, formatString).trim();
                } else {
                    thisStr = value;
                }
                thisStr = thisStr.replace("_", "").trim();
                break;
            case DATE: //日期
                thisStr = formatter.formatRawCellContents(Double.parseDouble(value), formatIndex, formatString);
                // 对日期字符串作特殊处理，去掉T
                thisStr = thisStr.replace("T", " ");
                break;
            default:
                thisStr = " ";
                break;
        }
        return thisStr;
    }
    
    /**
     * 根据前一个含值单元格的位置和当前单元格位置，计算中间多少个空格
     * */
    public int countNullCell(String ref, String preRef) {
        //excel2007最大行数是1048576，最大列数是16384，最后一列列名是XFD
        String xfd = ref.replaceAll("\\d+", "");
        String xfd_1 = "";
        //第一列特殊处理
        if(null == preRef) {
        	xfd_1 = "A";
        }else{xfd_1 = preRef.replaceAll("\\d+", "");}

        xfd = fillChar(xfd, 3, '@', true);
        xfd_1 = fillChar(xfd_1, 3, '@', true);
      
        
        char[] letter = xfd.toCharArray();
        char[] letter_1 = xfd_1.toCharArray();
        int res = (letter[0] - letter_1[0]) * 26 * 26 + (letter[1] - letter_1[1]) * 26 + (letter[2] - letter_1[2]);
        
        if(null == preRef) 
        {
        	return res;
        }else return res-1;
    }
    
    /**
     * 填充，补齐三位列号
     * */
    public String fillChar(String str, int len, char let, boolean isPre) {
        int len_1 = str.length();
        if (len_1 < len) {
            if (isPre) {
                for (int i = 0; i < (len - len_1); i++) {
                    str = let + str;
                }
            } else {
                for (int i = 0; i < (len - len_1); i++) {
                    str = str + let;
                }
            }
        }
        return str;
    }

    /**
     * @return the exceptionMessage
     */
    public String getExceptionMessage() {
        return exceptionMessage;
    }
    
    /**   
     * 每读一行调用一次，在方法中写自己的业务逻辑即可  
     * @param sheetIndex 工作簿序号  
     * @param curRow 处理到第几行  
     * @param rowList 当前数据行的数据集合  
     */    
    @SuppressWarnings("unchecked")
    public void optRow(int sheetIndex, int curRow, List<String> rowList) throws Exception{     

        if (curRow >=1) {  
        	if (rowList.size()>0){
            	dataList.add(rowList);
            }
        	//每1000行或者文档末尾写入数据库
            if (dataList.size() == 1000  || docEndFlag) { 
            	if(dataList.size()>0) {
            		try {
            			List<Object> insertDBList = insertDataToDB(dataList,rowCodeList, entityCode);
            			//线程计数器，业务关联性强，考虑放入threadHandler内处理
            			if(outterThreadPoolFlag) {
            				AutomicCounter.increase();
            			}
            			fixedThreadPool.execute(new DBProcessThread(insertDBList,entityCode));  
            		}catch(Exception e) {
            			throw e;
            		}
	                //dataList.clear();//不能使用clear(),否则写入库之前就清掉了
            		dataList = new ArrayList<List<String>>();
            	}
            }
        }  
    }     
    
    /**   
     * 每读一个单元格调用一次，加入cellList，如果xml校验开，对单元格进行验证
     * @param r 列号
     * @param v 值
     */    
    public void cellRuleValidate(int r,String v) throws SAXParseException{
    	if (validateByXMLFlag && null != parseXMLUtil && curRow>1) {
    		String ruleKey = entityCode + "_" + rowCodeList.get(r);
    		List<Map<String,String>> rulList = (List<Map<String,String>>)parseXMLUtil.columnRulesMap.get(ruleKey);
    		if(rulList != null && rulList.size()>0){
    			   for(int i=0 ; i<rulList.size() ; i++){
    				   Map<String,String> rulM = (Map<String,String>) rulList.get(i);
    				   String rulName = (String) rulM.get("name");
    				   String rulMsg = (String) rulM.get("message");
    				   if(!ExcelCellValidate.validate(rulName, v)){
    					   dataList.clear();  
    					   throw new SAXParseException(rulMsg, "publicId", "systemId", curRow, curCol+1);
    				   }
    			   }
    		   }
    		
    	}
    	cellList.add(r, v);
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
		String className = "com.ct.webDemo.common.entity." + BeanGSNameUtil.firstCharacterToUpper(entityCode);
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
	public boolean isMutiThreadFlag() {
		return mutiThreadFlag;
	}
	public void setMutiThreadFlag(boolean mutiThreadFlag) {
		this.mutiThreadFlag = mutiThreadFlag;
	}
	public boolean isOutterThreadPoolFlag() {
		return outterThreadPoolFlag;
	}
	public void setOutterThreadPoolFlag(boolean outterThreadPoolFlag) {
		this.outterThreadPoolFlag = outterThreadPoolFlag;
	}
    
    
}