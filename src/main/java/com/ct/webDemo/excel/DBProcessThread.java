package com.ct.webDemo.excel;



import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.ct.webDemo.base.ApplicationContextHelper;
import com.ct.webDemo.busi.service.DemoService;
import com.ct.webDemo.common.entity.Product;
import com.ct.webDemo.test.service.ServiceTest;

@Component
@Scope("prototype")//spring 多例
@SuppressWarnings("unchecked")
public class DBProcessThread<T> implements Runnable {
    private String msg;
    private static final Logger logger = LoggerFactory.getLogger(DBProcessThread.class);
    
    private List<T> insertList;
    
    private static DemoService demoService = ApplicationContextHelper.getBean(DemoService.class);
    
    public DBProcessThread (List<T> insertList) {
    	this.insertList = insertList;
    }
    
    @Override
    public void run() {
    	
    	long startTime = System.currentTimeMillis(); 
		
    	logger.info(">>>>>当前类型为：" + insertList.get(0).getClass().getName());
		demoService.save((List<Product>)insertList);
		
		long endTime = System.currentTimeMillis();  
        float seconds = (endTime - startTime) / 1000F;  
        logger.info(">>>>>当前线程执行时间为： " + Float.toString(seconds) + " seconds.");  
    }


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}