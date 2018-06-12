package com.ct.webDemo.threadPool;

import java.lang.reflect.Method;  
import java.util.concurrent.CountDownLatch;  
import java.util.concurrent.ThreadPoolExecutor;  
  
import org.slf4j.Logger;  
import org.slf4j.LoggerFactory;  
  
public class ThreadHandler<T> extends ThreadHandlerAbstract{  
    private static final Logger logger = LoggerFactory.getLogger(ThreadHandler.class);  
    protected T t;  
    protected Class<T> modelClass;  
    protected String method = "";  
      
      
      
    @SuppressWarnings("unchecked")  
    public ThreadHandler(Integer threadCount,T t){  
        this.t = t;  
        modelClass = (Class<T>) t.getClass();  
        if(null != threadCount){  
            super.countDownLatch = new CountDownLatch(threadCount);  
        }  
    }  
      
      
    @Override  
    public void run() {  
        try{  
            Method[] methods = this.modelClass.getMethods();  
              
            for (Method method : methods) {  
                if(method.getName().equals(this.method)){  
                    method.invoke(t);  
                }  
            }  
            if(null != super.countDownLatch){  
                super.countDownLatch.countDown();  
            }  
            if(null != ThreadPoolExecutorFactory.getThreadPoolExecutor().getQueue() && (ThreadPoolExecutorFactory.getThreadPoolExecutor().getQueue().size() < 20  
                    || ThreadPoolExecutorFactory.getThreadPoolExecutor().getQueue().size() == 0 )){  
                 ThreadPoolExecutorFactory.getThreadPoolExecutor().setCorePoolSize(20);  
            }else{  
                ThreadPoolExecutorFactory.getThreadPoolExecutor().setCorePoolSize(40);  
            }  
        }catch (Exception e) {  
            e.printStackTrace();  
            logger.error("线程池处理异常 方法:" + this.method,e);  
        }  
          
    }  
  
    @Override  
    public void execute(IThreadPoolExecutorHandler threadPoolExecutorHandler,String method)throws Exception{  
        this.method = method;  
          
        try {  
            ThreadPoolExecutor threadPoolExecutor = ThreadPoolExecutorFactory.getThreadPoolExecutor();  
            threadPoolExecutor.execute(threadPoolExecutorHandler);  
        } catch (Exception e) {  
            e.printStackTrace();  
            logger.error("线程池处理异常 execute 方法:" + this.method,e);  
            throw new Exception(e.getMessage(),e);  
        }  
      
    }  
  
  
    @Override  
    public void await() throws Exception {  
        try {  
            if(super.countDownLatch != null){  
                countDownLatch.await();  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
            logger.error("线程池处理异常 await 方法:" + this.method,e);  
            throw new Exception(e.getMessage(),e);  
        }  
          
    }  
  
  
    @Override  
    public void shutdown() throws Exception {  
        try {  
            ThreadPoolExecutor threadPoolExecutor = ThreadPoolExecutorFactory.getThreadPoolExecutor();  
            threadPoolExecutor.shutdown();  
        } catch (Exception e) {  
            e.printStackTrace();  
            logger.error("线程池处理异常 shutdown 方法:" + this.method,e);  
            throw new Exception(e.getMessage(),e);  
        }  
          
    }  
      
    public int getPoolSize(){  
        ThreadPoolExecutor threadPoolExecutor = ThreadPoolExecutorFactory.getThreadPoolExecutor();  
        return threadPoolExecutor.getPoolSize();  
          
    }  
      
    /** 
     * 获取线程池队列状态数量 
     * @return 
     */  
    public int getQueueSize(){  
        ThreadPoolExecutor threadPoolExecutor = ThreadPoolExecutorFactory.getThreadPoolExecutor();  
        return threadPoolExecutor.getQueue().size();  
    }  
      
      
    public static void main(String[] args) {  
        ThreadPoolExecutor threadPoolExecutor = ThreadPoolExecutorFactory.getThreadPoolExecutor();  
        for (int i = 0; i < 10000; i++) {  
            final int index = i;  
            threadPoolExecutor.execute(  
                new Runnable() {  
                    @Override  
                    public void run() {  
                        try {  
                            System.out.println(index + "队列数：" + ThreadPoolExecutorFactory.getThreadPoolExecutor().getQueue().size());  
                            Thread.sleep(200);  
                        } catch (InterruptedException e) {  
                            e.printStackTrace();  
                        }  
                    }  
                });  
  
        }  
    }  
      
}  