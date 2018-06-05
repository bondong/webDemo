package com.ct.webDemo.test.service;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.ct.webDemo.busi.service.DemoService;
import com.ct.webDemo.common.entity.Product;
import com.ct.webDemo.excel.ExcelReaderUtil;
import com.ct.webDemo.excel.ExportToExcelXLSX;
import com.ct.webDemo.test.util.JUnit4ClassRunner;
import com.ct.webDemo.util.BeanGSNameUtil;
import com.ct.webDemo.util.ParseXMLUtil;

@RunWith(JUnit4ClassRunner.class) //junit4.9以上版本
@ContextConfiguration(locations = {"classpath:application-test.xml","classpath:mybatis-test.xml"})//多个配置文件使用逗号隔开
@Rollback(value=false) //配置事务的回滚,对数据库的增删改都会回滚,便于测试用例的循环利用
@Transactional  //事务处理
public class ServiceTest{
	
	@Autowired
	private DemoService demoService;
	
	private static final Logger logger = LoggerFactory.getLogger(ServiceTest.class);
	

	//转换bean为数组，以供excel输出
	
	@Test
	public void methodExeTime() {
		long startTime = System.currentTimeMillis(); 
		
		//transData();
		importData();
		//Product product = demoService.get(183);for(int i=0;i<3000;i++) {insertDB(product);}
		long endTime = System.currentTimeMillis();  
        float seconds = (endTime - startTime) / 1000F;  
        logger.info(">>>>>执行时间为： " + Float.toString(seconds) + " seconds.");  
	}
	
	//
	public void importData() {
		String excelPath="D:/data.xlsx";
        String xmlPath = "src/main/resources/excelXml/product.xml";
        try {
        	ExcelReaderUtil.readExcel(excelPath,xmlPath);
        }catch (Exception e) {
        	e.printStackTrace();
        }
	}
	
	public void insertDataToDB(List<List<String>> dataList,List<String> rowCodeList,String entityCode) {
		//Class.forName("cn.classes.OneClass");
		List<Product> insertList = new ArrayList<Product>();
		StringBuffer json = new StringBuffer("");
		for (int i=0;i<dataList.size();i++) {
			List<String> data = dataList.get(i);
			if (data.size() == rowCodeList.size()) {
				json.append("{");
				for(int j=0 ;j<data.size(); j++) {
					//时间类型要特别注意
					json.append("'" + rowCodeList.get(j) + "':'" + data.get(j) + "'");
					if(j != data.size()-1) {
						json.append(",");
					}
				}
				json.append("}");
			}
			/*if(i != dataList.size()-1) {
				json.append(",");
			}*/
			logger.info(json.toString());
			Product product = JSON.parseObject(json.toString(), Product.class);
			insertList.add(product);
			json.setLength(0);
			logger.info(product.getName());
		}
		logger.info("" +insertList.size());
		//demoService.save(insertList);
		//json.append("]");
		
		
	}
	
	//
	public void transData() {
		File file = new File("src/main/resources/excelXml/product.xml");
		ParseXMLUtil parseXMLUtil = new ParseXMLUtil(file);
		List<String> fieldNames =  parseXMLUtil.getTableFieldsFromXml();
		List<String> rowNames = parseXMLUtil.getExcelFieldsFromXml();
		//数据库取数据
		List<Product> oData = extendData();
		
		List<Object[]>  dataList = new ArrayList<Object[]>();
		for (Product product : oData) {
			dataList.add(getValueByNames(product,fieldNames));
			//System.out.println(Arrays.toString(getValueByNames(product,fieldNames)));
		}
		ExportToExcelXLSX ex = new ExportToExcelXLSX("测试导出", rowNames, dataList,1000);
        try{
        	ex.exportData();
        }catch(Exception e) {e.printStackTrace();}
		
		dataList.clear();
	}
	
	//模拟从数据库获取数据
	public List<Product> extendData() {
		List<Product> d = new ArrayList<Product>();
		List<Product> products = demoService.getAllProduct();
		//库里记录，扩大5倍
		for (int i=0 ;i<1 ;i++) {
			d.addAll(products);
		}
		return d;
	}
	
	//反射bean转数组
	public Object[] getValueByNames(Object bean,List<String> names) {
		Object[] objs = new Object[names.size()];
		String name = "";
		for(int i = 0; i < names.size(); i++) {
			//System.out.println(name);
			name = names.get(i);
			try {  
	            //通过method的反射方法获取其属性值  
				//System.out.println(BeanGSNameUtil.getGetterMethodName(name));
	            Method method = bean.getClass().getMethod(BeanGSNameUtil.getGetterMethodName(name), new Class[]{});  
	            Type t = method.getAnnotatedReturnType().getType();
	            Object value = null;
	            //不处理的话时间会显示成为CST格式
	            if ("class java.util.Date".equals(t.toString())) {
	            	Date d = (Date) method.invoke(bean); 
	            	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	            	value = format.format(d);
	            }else {
	            	value = method.invoke(bean, new Object[]{});  
	            }
	            objs[i] = value;
	            //System.out.println(value);
	        } catch(Exception e) {
	        	
	        }
		}
		return objs;
	}
	
	//反射bean转数组,没有外部属性名输入
	public void getFieldGenericName(Object bean) {  
		Field[] fields = bean.getClass().getDeclaredFields();  
		Map<String,Object> m=new HashMap<String,Object>();  
		Field.setAccessible(fields, true);  
		for (int i = 0; i < fields.length;i++) {  
			Field field = fields[i];  
			try {  
				if(field.get(bean)==null)continue;  
				m.put(field.getName(), field.get(bean));  
			} catch (Exception e) {  
			}  
		}  
		String beanNames[]=m.keySet().toArray(new String[m.size()]);  
		Object[] values = m.values().toArray(new Object[m.size()]);  
		for(String s : beanNames){  
			System.out.println(s);  
		}  
		for(Object s : values){  
			System.out.println(s);  
		}  
	}  
	
	public void insertDB(Product product){
		product.setId(null);
		demoService.save(product);
	}
	
	
}