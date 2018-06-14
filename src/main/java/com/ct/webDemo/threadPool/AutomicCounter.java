package com.ct.webDemo.threadPool;

import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 基于AtomicInteger的计数器，线程安全
 * 用于解决需要等待某些线程执行完，但是线程总个数未知的情况，无法使用CountDownLanch
 * 
 * */
 
public class AutomicCounter{
	
	private static final Logger logger = LoggerFactory.getLogger(AutomicCounter.class);
    private static AutomicCounter instance;
    private static AtomicInteger counter = new AtomicInteger(0);
    private AutomicCounter() {}
    public static AutomicCounter getInstance() {
        if (instance == null) {
            instance = new AutomicCounter();
        }
        return instance;
    }
    
    public static int increase() {
    	logger.info("+++++AutomicCounter increase!");
    	return counter.incrementAndGet();
    }
    
    public static int decrease() {
    	logger.info("-----AutomicCounter decrease!");
    	if (counter.intValue()>0)
    		return counter.decrementAndGet();
    	else 
    		return 0;
    }
    
    public static boolean equelZero() {
    	logger.info(">>>>>AutomicCounter value is: " + counter.intValue());
    	if (counter.intValue()>0) {
    		return false;
    	}else 
    		return true;
    }
    
    public static void resetZero() {
    	logger.info(">>>>>AutomicCounter is resetted to zero!");
    	counter.set(0);
    }
    
}