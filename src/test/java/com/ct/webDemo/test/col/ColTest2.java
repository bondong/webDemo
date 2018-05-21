package com.ct.webDemo.test.col;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.transaction.annotation.Transactional;

import com.ct.webDemo.busi.col.DemoCol;
import com.ct.webDemo.test.util.JUnit4ClassRunner;


/**
 * @Author 
 * @create 
 * @Description : 未测试url,只简单调用了controller的方法
 **/
@RunWith(JUnit4ClassRunner.class) //junit4.9以上版本
@ContextConfiguration(locations = {"classpath:application-test.xml","classpath:mybatis-test.xml"})//多个配置文件使用逗号隔开
@Rollback(value=true) //配置事务的回滚,对数据库的增删改都会回滚,便于测试用例的循环利用
@Transactional  //事务处理
public class ColTest2 extends AbstractTransactionalJUnit4SpringContextTests {

	//mock模拟session,request,response
	private MockHttpSession session;
	private MockHttpServletRequest request;
	private MockHttpServletResponse response;
	
	private static final Logger logger = LoggerFactory.getLogger(ColTest.class);
	
	@Before
	public void setUp() throws Exception {
	
		this.session = new MockHttpSession();
		this.request = new MockHttpServletRequest();
		this.response = new MockHttpServletResponse();
	}
	
	@Test
	public void test() throws Exception {
	
		//创建参数,可添加对象至attribute
		//session.setAttribute("",);
		request.setSession(session);
		//创建参数,单值
		//String  s= "";int i= ;......
		 
		//构造controller
		DemoCol demoCol = (DemoCol) this.applicationContext.getBean("demoCol");
		demoCol.allProduct(request);
		logger.info("返回值：");
			
	}
}
