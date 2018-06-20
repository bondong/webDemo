package com.ct.webDemo.threadPool;

import java.lang.reflect.Method;  
import java.util.concurrent.CountDownLatch;  
import java.util.concurrent.ThreadPoolExecutor;  
  
import org.slf4j.Logger;  
import org.slf4j.LoggerFactory;  
@SuppressWarnings("all")    
public class ThreadHandler<T> extends ThreadHandlerAbstract{  
    private static final Logger logger = LoggerFactory.getLogger(ThreadHandler.class);  
    protected T t;  
    protected Class<T> modelClass;  
    protected String method = "";
    protected Object[] args = null;
    
      
    public ThreadHandler(Integer threadCount,T t){  
        this.t = t;  
        modelClass = (Class<T>) t.getClass();  
        if(null != threadCount && threadCount>0){  
            super.countDownLatch = new CountDownLatch(threadCount);  
        }  
    }  
    public ThreadHandler(AutomicCounter automicCounter,T t){  
        this.t = t;  
        modelClass = (Class<T>) t.getClass();  
        if(null != automicCounter ){  
            super.automicCounter = automicCounter;  
        }  
    }  
    public ThreadHandler(T t){  
        this.t = t;  
        modelClass = (Class<T>) t.getClass();  
    }  
    public ThreadHandler(T t,String method){  
        this.t = t; 
        this.method = method;
        modelClass = (Class<T>) t.getClass();
    }  
    public ThreadHandler(T t,String method,Object[] args){  
        this.t = t; 
        this.method = method;
        this.args = args;
        modelClass = (Class<T>) t.getClass();
        
    }  
      
    @Override  
    public void run() {  
        try{  
        	//Object obj = t.getClass().newInstance();
        	
        	if (null !=args && args.length>0) {
	        	Class[] argsClass = new Class[args.length]; 
	            for (int i = 0, j = args.length; i < j; i++) { 
	                argsClass[i] = args[i].getClass(); 
	            } 
	            Method m =t.getClass().getDeclaredMethod(method,argsClass);
	            m.invoke(t, args);
        	}else {
        		Method m =t.getClass().getDeclaredMethod(method);
        		m.invoke(t);
        	}
        	
            /*Method[] methods = this.modelClass.getMethods();    
            for (Method method : methods) {  
                if(method.getName().equals(this.method)){  
                    method.invoke(t);  
                }  
            }  */
        	
        	if(null != super.automicCounter){  
                super.automicCounter.decrease();
            }
            if(null != super.countDownLatch){  
                super.countDownLatch.countDown();  
            }  
            /*if(null != ThreadPoolExecutorFactory.getThreadPoolExecutor().getQueue() 
            		&& (ThreadPoolExecutorFactory.getThreadPoolExecutor().getQueue().size() < 20  
                    || ThreadPoolExecutorFactory.getThreadPoolExecutor().getQueue().size() == 0 )){  
                 ThreadPoolExecutorFactory.getThreadPoolExecutor().setCorePoolSize(20);  
            }else{  
                ThreadPoolExecutorFactory.getThreadPoolExecutor().setCorePoolSize(40);  
            }*/  
        }catch (Exception e) {  
            e.printStackTrace();  
            logger.error("线程池处理异常 方法:" + this.method,e);  
        }  
          
    }  
  
    @Override  
    public void execute(Runnable threadPoolExecutorHandler,String method)throws Exception{  
        this.method = method;  
          
        try {  
            ThreadPoolExecutor threadPoolExecutor = ThreadPoolManager.getThreadPoolExecutor();  
            threadPoolExecutor.execute(threadPoolExecutorHandler);  
        } catch (Exception e) {  
            e.printStackTrace();  
            logger.error("线程池处理异常 execute 方法:" + this.method,e);  
            throw new Exception(e.getMessage(),e);  
        }  
      
    }  
  
    @Override  
    public void countDownAwait() throws Exception {  
        try {  
            if(super.countDownLatch != null){  
                countDownLatch.await();  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
            throw new Exception(e.getMessage(),e);  
        }  
    }  
    
    
    public static void main(String[] args) {  
        ThreadPoolExecutor threadPoolExecutor = ThreadPoolManager.getThreadPoolExecutor();  
        
		ThreadHandler<TestClass> handler = new ThreadHandler(new TestClass(),"testPrint");
		threadPoolExecutor.execute(handler);
		
		Object[] arg = new Object[2];
		arg[0] = new Integer(5);
		arg[1] = new String("String");
		ThreadHandler<TestClass> handler2 = new ThreadHandler(new TestClass(),"testPrint",arg);
		threadPoolExecutor.execute(handler2);
		
		ThreadPoolManager.getThreadPoolExecutor().shutdown();
    }  
    
}  