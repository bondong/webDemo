package com.ct.webDemo.servlet;

import java.io.IOException;  
import javax.servlet.ServletException;  
import javax.servlet.http.HttpServlet;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;  


public class FileExitScanServlet extends HttpServlet{  
	private static final long serialVersionUID = 1L;  
	private FileExitScanThread fileExitScanThread;  
	public FileExitScanServlet(){}  
	
	public void init(){  
		String str = null;  
		if (str == null && fileExitScanThread == null) {  
			fileExitScanThread = new FileExitScanThread();  
			fileExitScanThread.start(); // servlet 上下文初始化时启动 socket  
	    }  
	}  
	
	public void doGet(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse)  
	throws ServletException, IOException{}  
	
	public void destory(){  
		if (fileExitScanThread != null && fileExitScanThread.isInterrupted()) {  
			fileExitScanThread.interrupt();  
	    }  
	}  
}  