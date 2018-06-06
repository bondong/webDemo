package com.ct.webDemo.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 解析xml工具类,支持一个XML文件配置多个EXCEL设置
 *
 */
@SuppressWarnings("rawtypes")
public class ParseXMLUtil {
	
	public List<String> getEntityList() {
		return entityList;
	}

	public void setEntityList(List<String> entityList) {
		this.entityList = entityList;
	}

	/**entity 名list**/
	public List<String> entityList;
	/**entity map对象，key:code ,value:entity的属性map集**/
	public Map<String,Map<String,String>> entityMap ;
	
	/**column map 对象，key:entityCode_colCode , value:column的属性map集 **/
	public Map<String,Map<String,String>> columnMap;
	
	/**rule map 对象，key:entityCode_colCode_ruleCode, value: rule 的map集：找到一行rule**/
	public Map<String,Map<String,String>> ruleMap ;
	
	/**rules map 对象, key:entityCode_colCode, value: rules 的map集:找到该column下所有的rule**/
	public Map<String,List<Map<String,String>>> columnRulesMap ;
	
	/**entity--column map: key:entityCode, value: column list:根据实体类名得到所有的列**/
	public Map<String,List<Map<String,String>>> columnListMap ;
	
	/**column list 所有列，一个xml只配置一个entity时有效**/
	public List<Map<String,String>> columnList ;
	
	 
    /**开始解析xml文件**/
	public ParseXMLUtil(File xmlFilePath){
		FileInputStream in = null;
		try {
			if(xmlFilePath == null){
				 throw new FileNotFoundException();
			}
			SAXReader reader = new SAXReader();		           
           	in = new FileInputStream(xmlFilePath);	           	
			Document doc = reader.read(in);
			Element root = doc.getRootElement();	
			Iterator itEntity = root.elements("entity").iterator();
			while(itEntity.hasNext()){
				Element entity = (Element) itEntity.next();
				parseEntity(entity);
			}
			//System.out.println("parse end!");
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	 /**开始解析entity**/
	public void parseEntity(Element entity){
		if(entity != null){
			
			/**对数据进行初始化设置**/
			entityList = new ArrayList<String>();
			columnListMap = new HashMap<String,List<Map<String,String>>>();
			columnMap = new HashMap<String,Map<String,String>>();
			entityMap = new HashMap<String,Map<String,String>>();
			ruleMap = new HashMap<String,Map<String,String>>();
			columnRulesMap = new HashMap<String,List<Map<String,String>>>();
			columnList = new ArrayList<Map<String,String>>();
			
			setEntityMap(entity);			
			String entityName = entity.attributeValue("name");
			String entityCode = entity.attributeValue("code");
			Iterator itColumn = entity.elements("column").iterator();
			while(itColumn.hasNext()){
				Element column = (Element) itColumn.next();
				setColumnMap(entityCode,column);
			}
			columnListMap.put(entityCode, columnList);
			entityList.add(entityCode);
		}
	}
	 
	
	
	/**将entity放入entityMap中**/
	@SuppressWarnings("unchecked")
	public void setEntityMap(Element entity){		
		Map ent = new HashMap<String,String>();
		String name = entity.attributeValue("name");
		String code = entity.attributeValue("code");
		ent.put("name", name);
		ent.put("code", code);
		entityMap.put(code, ent);			
	}
	
	/**将column放入columnMap中**/
	@SuppressWarnings("unchecked")
	public void setColumnMap(String entityCode,Element column){
		if(column != null){		
			Map col = new HashMap<String,String>();
			String name = column.attributeValue("name");
			String code = column.attributeValue("code");
			String type = column.attributeValue("type");
			String property = column.attributeValue("property");
			col.put("name", name);
			col.put("code", code);
			col.put("type", type);
			col.put("property", property);
			String columnMapKey = entityCode+"_"+code;    //eg:  用户表_用户名
			columnMap.put(columnMapKey, col);		
			columnList.add(col);
			Iterator ruleIt = column.elements("rules").iterator();  //获得rules
			while(ruleIt.hasNext()){
				Element rules = (Element)ruleIt.next(); 
    			Iterator rule  = rules.elements("rule").iterator();   //获得 rule
    			while(rule.hasNext()){
    				Element ruleValid = (Element) rule.next();     //获得每一行rule
    				setRuleMap(entityCode,property,ruleValid);    				
    			}
			}
		}
	}
		
    /**将 rule 验证规则放入ruleMap中**/
	@SuppressWarnings("unchecked")
	public void setRuleMap(String entityCode,String columnProperty,Element ruleValid){
		if(ruleValid != null){			
			String ruleCode = ruleValid.attributeValue("code");
			String ruleMsg = ruleValid.attributeValue("message");
			Map ruleValidMap = new HashMap<String,String>();
			ruleValidMap.put("name", ruleCode);
			ruleValidMap.put("message", ruleMsg);
			String ruleStrKey = entityCode+"_"+columnProperty+"_"+ruleCode;
			String colStrKey = entityCode+"_"+columnProperty;
			if(this.getColumnRulesMap().containsKey(colStrKey)){
    			List valids = (List) this.getColumnRulesMap().get(colStrKey);
    			valids.add(ruleValidMap);
    		}else{
    			List valids = new ArrayList();
    			valids.add(ruleValidMap);
    			this.columnRulesMap.put(colStrKey, valids);  //将每个column下的所有rules存入该map中
    		}
			ruleMap.put(ruleStrKey, ruleValidMap); //将每个column下的一条rule存入该map中
		}
	}

	/**所有的get set 方法**/
	public Map<String, Map<String, String>> getEntityMap() {
		return entityMap;
	}

	public void setEntityMap(Map<String, Map<String, String>> entityMap) {
		this.entityMap = entityMap;
	}

	public Map<String, Map<String, String>> getColumnMap() {
		return columnMap;
	}

	public void setColumnMap(Map<String, Map<String, String>> columnMap) {
		this.columnMap = columnMap;
	}

	public Map<String, Map<String, String>> getRuleMap() {
		return ruleMap;
	}

	public void setRuleMap(Map<String, Map<String, String>> ruleMap) {
		this.ruleMap = ruleMap;
	}

	public Map<String, List<Map<String, String>>> getColumnRulesMap() {
		return columnRulesMap;
	}

	public void setColumnRulesMap(Map<String, List<Map<String, String>>> columnRulesMap) {
		this.columnRulesMap = columnRulesMap;
	}

	public Map<String, List<Map<String, String>>> getColumnListMap() {
		return columnListMap;
	}

	public void setColumnListMap(Map<String, List<Map<String, String>>> columnListMap) {
		this.columnListMap = columnListMap;
	}

	public List<Map<String, String>> getColumnList() {
		return columnList;
	}

	public void setColumnList(List<Map<String, String>> columnList) {
		this.columnList = columnList;
	}
	

	/** 获取列名转化为bean后的属性名*/
	public List<String> getBeanPropertiesFromXmlByEntity(String entityCode){
		List<Map<String,String>> columns = columnListMap.get(entityCode);
		List<String> beanFields = new ArrayList<String>();  
		if (!columnList.isEmpty()) {
			for (Map<String,String> m : columns) {
				beanFields.add(m.get("property").toString());
			}
		}
		return beanFields;
	}
	
	/** 获取中文列名集合*/
	public List<String> getColumnNameFromXmlByEntity(String entityCode){
		List<Map<String,String>> columns = columnListMap.get(entityCode);
		List<String> excelNameFields = new ArrayList<String>();  
		if (!columnList.isEmpty()) {
			for (Map<String,String> m : columns) {
				excelNameFields.add(m.get("name").toString());
			}
		}
		return excelNameFields;
	}
	
	/** 获取列数据库字段名**/
	public List<String> getColumnDBNameFromXmlByEntity(String entityCode){
		List<Map<String,String>> columns = columnListMap.get(entityCode);
		List<String> excelNameFields = new ArrayList<String>();  
		if (!columnList.isEmpty()) {
			for (Map<String,String> m : columns) {
				excelNameFields.add(m.get("code").toString());
			}
		}
		return excelNameFields;
	}
	
	/** 获取列名转化为bean后的属性名*/
	public List<String> getBeanPropertiesFromXml(){
		List<String> beanFields = new ArrayList<String>();  
		if (!columnList.isEmpty()) {
			for (Map<String,String> m : columnList) {
				beanFields.add(m.get("property").toString());
			}
		}
		return beanFields;
	}
	
	/** 从xml配置文件获取数据库字段名*/
	public List<String> getTableFieldsFromXml(){
		List<String> tableFields = new ArrayList<String>();  
		if (!columnList.isEmpty()) {
			for (Map<String,String> m : columnList) {
				tableFields.add(m.get("code").toString());
			}
		}
		return tableFields;
	}
	
	/** 从xml配置文件获取中文列名*/
	public List<String> getExcelFieldsFromXml(){
		List<String> tableFields = new ArrayList<String>();  
		if (!columnList.isEmpty()) {
			for (Map<String,String> m : columnList) {
				tableFields.add(m.get("name").toString());
			}
		}
		return tableFields;
	}


	public static void main(String[] args) {
		/*
		//也可获取路径，但必须对路径中的空格进行处理
		String url = ParseXMLUtil.class.getResource("/excelXml/user.xml").getFile();
		try {
			url = URLDecoder.decode(url,"utf-8");
		}catch(Exception e) {
			
		}
		*/
		File file = new File("src/main/resources/excelXml/user.xml");
		new ParseXMLUtil(file);		
	}
     


}
