package com.ct.webDemo.servlet;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ct.webDemo.excel.ExcelReaderUtil;
import com.ct.webDemo.threadPool.AutomicCounter;
import com.ct.webDemo.threadPool.ThreadHandler;
import com.ct.webDemo.threadPool.ThreadPoolManager;
import com.ct.webDemo.threadPool.WorkQueue;
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
		

		WorkQueue workQueue = WorkQueue.getInstance();
		FileUtils fileRecursiveScan = new FileUtils();
		ExcelReaderUtil excelReaderUtil = new ExcelReaderUtil();
		List<String> fileNames = new ArrayList<String>();
		ThreadPoolExecutor threadPoolExecutor = ThreadPoolManager.getThreadPoolExecutor();
		while (!finished) {
			logger.info("____Thread excute time:" + System.currentTimeMillis());  
			try {  
                Thread.sleep(30000);  
                fileNames = fileRecursiveScan.fileList();
            } catch (InterruptedException e) {  
                //e.printStackTrace();  
            	logger.error( "thread was interrupted ... " );
            } catch (Exception e) {
            	e.printStackTrace();  
            } 
            
			if (fileNames.size()>0) {
				for(String fileName : fileNames) {
					logger.info(">>>>>current file :" + fileName);
					workQueue.getTaskQueue().add(fileName);
				}
				fileNames.clear();
				logger.info(">>>>>current fileName length is  :" + fileNames.size());
				logger.info(">>>>>current workQueue length is  :" + workQueue.getTaskQueue().size());
				
				while(workQueue.getTaskQueue().size() != 0){
					String file = (String)workQueue.getTaskQueue().removeFirst();
					logger.info(">>>>>current queue file :" + file);
					AutomicCounter automicCounter = AutomicCounter.getInstance();
					AutomicCounter.resetZero();
					
					//模拟每组任务包含读取三个文件
					for(int i=0;i<3;i++) {
						excelReaderUtil.setExcelName("data.xlsx");
						excelReaderUtil.setXmlName("product.xml");
						ThreadHandler<ExcelReaderUtil> handler = new ThreadHandler<ExcelReaderUtil>(excelReaderUtil,"readExcel");
						handler.setAutomicCounter(automicCounter);
						try {
							AutomicCounter.increase();
							ThreadPoolManager.getThreadPoolExecutor().execute(handler);
						}catch (Exception e) {
							e.printStackTrace();
						}
					}
					
					while (!AutomicCounter.equelZero()) {
						logger.info(">>>>>now waitting......thread count is :" + ThreadPoolManager.getThreadPoolExecutor().getActiveCount()
								+ ",taks queue is :" + ThreadPoolManager.getThreadPoolExecutor().getTaskCount()
								+",thread max count is :" + ThreadPoolManager.getThreadPoolExecutor().getLargestPoolSize());
						try{Thread.sleep(1000);}catch(Exception e){}
					}
					
					//以下执行存储过程：校验、连接、临时表转正式表
					
				}
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