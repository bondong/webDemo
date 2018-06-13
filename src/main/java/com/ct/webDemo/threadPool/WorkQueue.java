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
	
	//任务缓冲队列,适用于大并发
    private static Queue<Object> msgQueue = null;
    //任务集,防止重复提交,适用于大并发
    private static Map<String, Object> taskMap = null;
   
    //简单任务集,适用于低并发
    private static Queue<Object> taskQueue = null;
    
    //并发标记
    private static boolean highConcurrenceMode = false;
    
    //单例，暂不用考虑线程安全
    private static WorkQueue instance;  
    private WorkQueue (boolean highConcurrenceMode){
    	this.highConcurrenceMode = highConcurrenceMode;
    	if (highConcurrenceMode) {
    		msgQueue = new LinkedList<Object>();
    		taskMap = new ConcurrentHashMap<>();
    		logger.info(">>>>>highConcurrenceMode is ture!" );
    	}else {
    		taskQueue = new LinkedList<Object>();
    		logger.info(">>>>>highConcurrenceMode is false!" );
    	}
    }   
    public static WorkQueue getInstance(boolean highConcurrenceMode) {  
	    if (instance == null) {  
	    	instance = new WorkQueue(highConcurrenceMode);  
	    }  
	    return instance;  
    }
    
    
	public Queue<Object> getMsgQueue() {
		return msgQueue;
	}
	public void setMsgQueue(Queue<Object> msgQueue) {
		WorkQueue.msgQueue = msgQueue;
	}
	public Map<String, Object> getTaskMap() {
		return taskMap;
	}
	public void setTaskMap(Map<String, Object> taskMap) {
		WorkQueue.taskMap = taskMap;
	}
	public Queue<Object> getTaskQueue() {
		return taskQueue;
	}
	public void setTaskQueue(Queue<Object> taskQueue) {
		WorkQueue.taskQueue = taskQueue;
	}
	public boolean isHighConcurrenceMode() {
		return highConcurrenceMode;
	}
	public void setHighConcurrenceMode(boolean highConcurrenceMode) {
		WorkQueue.highConcurrenceMode = highConcurrenceMode;
	}
	
    
    
}