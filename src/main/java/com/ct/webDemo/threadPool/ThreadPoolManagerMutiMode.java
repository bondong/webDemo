package com.ct.webDemo.threadPool;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
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
public class ThreadPoolManagerMutiMode {
    private static Logger logger = LoggerFactory.getLogger(ThreadPoolManagerMutiMode.class);

    // 线程池维护线程的最少数量
    private final static int CORE_POOL_SIZE = 2;
    // 线程池维护线程的最大数量
    private final static int MAX_POOL_SIZE = 10;
    // 线程池维护线程所允许的空闲时间
    private final static int KEEP_ALIVE_TIME = 10;
    // 线程池所使用的缓冲队列大小
    private final static int WORK_QUEUE_SIZE = 50;
    
    //任务缓冲队列,适用于大并发
    private static Queue<Object> msgQueue = new LinkedList<Object>();
    //任务集,防止重复提交,适用于大并发
    private static Map<String, Object> taskMap = new ConcurrentHashMap<>();
    
    //简单任务集,适用于低并发
    private static Queue<Object> taskQueue = new LinkedList<Object>();
    
    //订单线程池
    private static ThreadPoolExecutor threadPool = null;
    
    //构造函数根据MODE参数生成普通线程池或 有界队列线程池+调度线程池
    
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


}