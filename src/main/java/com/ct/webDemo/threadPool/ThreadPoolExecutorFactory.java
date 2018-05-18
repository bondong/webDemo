package com.ct.webDemo.threadPool;


import java.util.concurrent.LinkedBlockingQueue;  
import java.util.concurrent.ThreadPoolExecutor;  
import java.util.concurrent.TimeUnit;  
  
/** 
 * 处理内容:线程池工厂类 
 */  
public class ThreadPoolExecutorFactory {  
    /** 
     * corePoolSize 池中所保存的线程数，包括空闲线程。   
     */  
    private static final int corePoolSize = 10;  
    /** 
     * maximumPoolSize - 池中允许的最大线程数(采用LinkedBlockingQueue时没有作用)。   
     */  
    private static final int maximumPoolSize = 10;  
    /** 
     * keepAliveTime -当线程数大于核心时，此为终止前多余的空闲线程等待新任务的最长时间，线程池维护线程所允许的空闲时间 
     */  
    private static final int keepAliveTime = 60;  
      
    /** 
     * 执行前用于保持任务的队列（缓冲队列） 
     */  
    private static final int capacity = 100;  
      
    /** 
     * 线程池对象 
     */  
    private static ThreadPoolExecutor threadPoolExecutor = null;  
      
    //构造方法私有化  
    private ThreadPoolExecutorFactory(){}  
      
    public static ThreadPoolExecutor getThreadPoolExecutor(){  
        if(null == threadPoolExecutor){  
            ThreadPoolExecutor t;  
            synchronized (ThreadPoolExecutor.class) {  
                t = threadPoolExecutor;  
                if(null == t){  
                    synchronized (ThreadPoolExecutor.class) {  
                        t = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.MILLISECONDS,new LinkedBlockingQueue<Runnable>(),new ThreadPoolExecutor.DiscardOldestPolicy());  
                    }  
                    threadPoolExecutor = t;  
                }  
            }  
        }  
        return threadPoolExecutor;  
    }  
}  