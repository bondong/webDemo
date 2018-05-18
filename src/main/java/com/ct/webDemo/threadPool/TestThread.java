package com.ct.webDemo.threadPool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")//spring 多例
public class TestThread implements Runnable {
    private String msg;
    private Logger log = LoggerFactory.getLogger(TestThread.class);

    @Autowired
    
    @Override
    public void run() {
        //模拟在数据库插入数据
        log.info("insert->" + msg);
    }


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}