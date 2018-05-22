package com.ct.webDemo.test.service;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
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
import com.ct.webDemo.test.util.JUnit4ClassRunner;
import com.ct.webDemo.util.BeanGSNameUtil;

@RunWith(JUnit4ClassRunner.class) //junit4.9以上版本
@ContextConfiguration(locations = {"classpath:application-test.xml","classpath:mybatis-test.xml"})//多个配置文件使用逗号隔开
@Rollback(value=true) //配置事务的回滚,对数据库的增删改都会回滚,便于测试用例的循环利用
@Transactional  //事务处理
public class ServiceTest{
	
	@Autowired
	private DemoService demoService;
	
	private static final Logger logger = LoggerFactory.getLogger(ServiceTest.class);
	
	@Test
	public void test() throws Exception {		
		logger.info(">>>>return info is :" + demoService.getTableFieldsFromXml("src/main/resources/excelXml/product.xml").toString());
		logger.info(">>>>return info is :" + demoService.getTablePropertiesFromXml("src/main/resources/excelXml/product.xml").toString());
	}
	
	@Test
	public void test2() throws Exception {
		
		/*ExportToExcelXLSX exportToExcelXLSX = new ExportToExcelXLSX("test",
				demoService.getTableFieldsFromXml("src/main/resources/excelXml/product.xml"),
				demoService.getAll(),
				1000);*/
		getFieldByNames(demoService.get(183),
				demoService.getTablePropertiesFromXml("src/main/resources/excelXml/product.xml"));
		//logger.info(">>>>return info is :" + demoService.get(183).toString());
	}
	
	public List<Product> extendData() {
		List<Product> d = new ArrayList<Product>();
		List<Product> products = demoService.getAll();
		//库里10条记录，扩大100倍
		for (int i=1 ;i<100 ;i++) {
			d.addAll(products);
		}
		return d;
	}
	
	public void getFieldByNames(Object bean,List<String> names) {
		Object[] objs = new Object[names.size()];
		String name = "";
		for(int i = 0; i < names.size(); i++) {
			name = names.get(i);
			try {  
	            // 通过method的反射方法获取其属性值  
	            Method method = bean.getClass().getMethod(BeanGSNameUtil.getGetterMethodName(name), new Class[]{});  
	            Object value = method.invoke(bean, new Object[]{});  
	            objs[i] = value;
	            System.out.println(value);
	        } catch(Exception e) {
	        	
	        }
		}
		
	}
	
	//反射bean转数组
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
	
	
	
}