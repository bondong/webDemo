package com.ct.webDemo.threadPool;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.stereotype.Component;

/**
 * @description threadPool订单线程池, 处理订单
 * scheduler 调度线程池 用于处理订单线程池由于超出线程范围和队列容量而不能处理的订单
 */
@SuppressWarnings("all")
public class ThreadPoolManager {
    private static Logger logger = LoggerFactory.getLogger(ThreadPoolManager.class);

    // 线程池维护线程的最少数量
    private final static int CORE_POOL_SIZE = 10;
    // 线程池维护线程的最大数量
    private final static int MAX_POOL_SIZE = 20;
    // 线程池维护线程所允许的空闲时间
    private final static int KEEP_ALIVE_TIME = 10;
    
    private CountDownLatch countDownLatch = null;  
    
    //构造方法私有化，单例模式，双重校验锁
    private ThreadPoolManager() {}
    
    //订单线程池
    private static ThreadPoolExecutor threadPool = null;
    
    public static ThreadPoolExecutor getThreadPoolExecutor(){  
        if(null == threadPool){  
            ThreadPoolExecutor t;  
            synchronized (ThreadPoolExecutor.class) {  
                t = threadPool;  
                if(null == t){  
                    synchronized (ThreadPoolExecutor.class) {  
                        t = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_TIME, 
                        		TimeUnit.MILLISECONDS,new LinkedBlockingQueue<Runnable>(),
                        		new ThreadPoolExecutor.AbortPolicy());  
                    }  
                    threadPool = t;  
                }  
            }  
        }  
        return threadPool;  
    }  
    
    public void countDownAwait() throws Exception {  
        try {  
            if(countDownLatch != null){  
                countDownLatch.await();  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
            throw new Exception(e.getMessage(),e);  
        }  
    }  
    
    public void await() throws Exception {  
        try {  
        	threadPool.wait();
        } catch (Exception e) {  
            e.printStackTrace();  
            throw new Exception(e.getMessage(),e);  
        }  
    } 
    
    public static void shutdown() throws Exception {  
        try {  
        	threadPool.shutdown();  
        } catch (Exception e) {  
            e.printStackTrace();  
            throw new Exception(e.getMessage(),e);  
        }  
    }
    
    public int getQueueSize(){
    	if (null!=threadPool)
    		return threadPool.getQueue().size();  
    	else 
    		return 0;
    }
    
    public int getPoolSize(){  
    	if (null!=threadPool)
    		return threadPool.getPoolSize();    
    	else 
    		return 0;
    }
    
    public CountDownLatch getCountDownLatch() {
		return countDownLatch;
	}

	public void setCountDownLatch(CountDownLatch countDownLatch) {
		this.countDownLatch = countDownLatch;
	}


}