package com.ct.webDemo.threadPool;

import java.util.concurrent.CountDownLatch;  

public abstract class ThreadHandlerAbstract implements Runnable {  
   
    protected CountDownLatch countDownLatch = null;  
    
    AutomicCounter automicCounter = null;
    
    public ThreadHandlerAbstract(CountDownLatch countDownLatch){  
        this.countDownLatch = countDownLatch;  
    }  
      
    public ThreadHandlerAbstract(){}  
      
    public abstract void execute(Runnable threadPoolExecutorHandler,String method)throws Exception;  
      
    public abstract void countDownAwait()throws Exception;

	public CountDownLatch getCountDownLatch() {
		return countDownLatch;
	}

	public void setCountDownLatch(CountDownLatch countDownLatch) {
		this.countDownLatch = countDownLatch;
	}

	public AutomicCounter getAutomicCounter() {
		return automicCounter;
	}

	public void setAutomicCounter(AutomicCounter automicCounter) {
		this.automicCounter = automicCounter;
	}  
     
}  