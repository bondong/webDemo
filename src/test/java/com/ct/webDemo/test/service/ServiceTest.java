package com.ct.webDemo.test.service;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
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

import com.ct.webDemo.busi.service.DemoService;
import com.ct.webDemo.common.entity.Product;
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
		
		transData();
		//Product product = demoService.get(183);for(int i=0;i<3000;i++) {insertDB(product);}
		long endTime = System.currentTimeMillis();  
        float seconds = (endTime - startTime) / 1000F;  
        logger.info(">>>>>执行时间为： " + Float.toString(seconds) + " seconds.");  
	}
	
	//
	public void transData() {
		File file = new File("src/main/resources/excelXml/product.xml");
		ParseXMLUtil parseXMLUtil = new ParseXMLUtil(file);
		List<String> fieldNames =  parseXMLUtil.getTableFieldsFromXml();
		List<String> rowNames = parseXMLUtil.getExcelFieldsFromXml();
		//数据库取数据，并扩大
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
	            Object value = method.invoke(bean, new Object[]{});  
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