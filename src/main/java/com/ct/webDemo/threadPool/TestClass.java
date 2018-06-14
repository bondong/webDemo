package com.ct.webDemo.threadPool;

import java.util.concurrent.ThreadPoolExecutor;

import com.ct.webDemo.excel.ExcelReaderUtil;

public class TestClass {
	
	public void testPrint() {
		System.out.println(">>>>>test threadHandler! success!");
	}
	
	public void testPrint(Integer i,String s) {
		System.out.println(">>>>>test threadHandler! success! and para are: " + i + ", " + s);
	}
}