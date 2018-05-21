package com.ct.webDemo.test.col;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.ct.webDemo.busi.col.DemoCol;
import com.ct.webDemo.common.entity.Product;
import com.ct.webDemo.test.util.JUnit4ClassRunner;


/**
 * @Author 
 * @create 
 * @Description : 
 **/
@RunWith(JUnit4ClassRunner.class) //junit4.9以上版本
@ContextConfiguration(locations = {"classpath:application-test.xml","classpath:mybatis-test.xml"})//多个配置文件使用逗号隔开
@Rollback(value=true) //配置事务的回滚,对数据库的增删改都会回滚,便于测试用例的循环利用
@Transactional  //事务处理
public class ColTest {
	@Autowired
	private DemoCol demoCol;
	
	private MockMvc mockMvc;
	
	private static final Logger logger = LoggerFactory.getLogger(ColTest.class);

	@Before
	public void setup(){
	    mockMvc = MockMvcBuilders.standaloneSetup(demoCol).build();
	}

	@Test
	public void test1() throws Exception {
		
		Product p = new Product();
		p.setId(183);
		String requestJson = JSONObject.toJSONString(p);
		logger.info(requestJson);
		
		//有请求头、有请求体requestJson的post请求
	    ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.post("/col/productById")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(requestJson)
	            .param("id", "183"))
			    .andDo(print())
			    .andExpect(status().isOk());
	    MvcResult mvcResult = resultActions.andReturn();
	    String result = mvcResult.getResponse().getContentAsString();
	    logger.info("return data:" + result);
	    // 也可以从response里面取状态码，header,cookies...
		//System.out.println(mvcResult.getResponse().getStatus());
	}

	/*
	//有请求头、无参数的get请求
	@Test
	public void test2() throws Exception {
		ResultActions reaction=this.mockMvc.perform(MockMvcRequestBuilders.get("/service/test/testController")
				.accept(MediaType.APPLICATION_JSON)//返回值接收json
				.header("Timestamp", "1496656373783")
				.header("AppId", "1003"));
		reaction.andExpect(MockMvcResultMatchers.status().isOk());
		MvcResult mvcResult =reaction.andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
	}*/
	
}



