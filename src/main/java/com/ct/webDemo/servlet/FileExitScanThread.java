package com.ct.webDemo.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ct.webDemo.util.FileUtils;

class FileExitScanThread extends Thread {  
	
	private volatile boolean finished = false;
	private static Logger logger = LoggerFactory.getLogger(FileExitScanThread.class);
	public void stopMe() {
        finished = true;
        //如果执行代码阻塞于wait()或sleep()时，线程不能立刻检测到条件变量。因此此处可调用interrupt()
        //线程处为wait或sleep时，调用interrupt()会抛出异常
        interrupt();
        	
    }
	public void run() {  
		
		FileUtils fileRecursiveScan = new FileUtils();
		
		while (!finished) {
			logger.info("____Thread excute time:" + System.currentTimeMillis());  
			try {  
                Thread.sleep(30000);  
                fileRecursiveScan.fileList();
            } catch (InterruptedException e) {  
                //e.printStackTrace();  
            	logger.error( "thread was interrupted ... " );
            } catch (Exception e) {
            	e.printStackTrace();  
            } 
            
        }  
		logger.info( "Thread exiting under request..." );  
    }  
	
	
	
	
	
	public static void main( String args[] ) throws Exception {  
		FileExitScanThread thread = new FileExitScanThread();  
		System.out.println( "Starting thread..." );  
		thread.start();  
		Thread.sleep( 4000 );  
		System.out.println( "Asking thread to stop..." );  
		thread.stopMe(); 
		System.out.println( "End thread to stop..." );
		System.out.println( "Stopping application..." );  
  }  
	
}   