package com.ct.webDemo.threadPool;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ct.webDemo.util.FileUtils;

@SuppressWarnings("all")
public class WorkQueue
{	
	private static Logger logger = LoggerFactory.getLogger(WorkQueue.class);
	
	/*//任务缓冲队列,适用于较大并发
    private static Queue<Object> msgQueue = null;
    //任务集,防止重复提交,适用于大并发
    private static Map<String, Object> taskMap = null;*/
   
    //简单任务集,适用于低并发
    private static LinkedList<Object> taskQueue = null;
    
    //并发标记
    private static boolean highConcurrenceMode = false;
    
    private static WorkQueue instance;  
    private WorkQueue (){
    	
		/*msgQueue = new LinkedList<Object>();
		taskMap = new ConcurrentHashMap<String, Object>();*/
		taskQueue = new LinkedList<Object>();
		
	}
  
    public static WorkQueue getInstance() {  
	    if (instance == null) {  
	    	instance = new WorkQueue();  
	    }  
	    return instance;  
    }
    
    
	public LinkedList<Object> getTaskQueue() {
		return taskQueue;
	}
	public void setTaskQueue(LinkedList<Object> taskQueue) {
		WorkQueue.taskQueue = taskQueue;
	}
	public boolean isHighConcurrenceMode() {
		return highConcurrenceMode;
	}
	public void setHighConcurrenceMode(boolean highConcurrenceMode) {
		WorkQueue.highConcurrenceMode = highConcurrenceMode;
	}
	
    
    
}