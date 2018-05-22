package com.ct.webDemo.busi.service;

import java.util.List;
import java.util.Map;

import com.ct.webDemo.base.BaseService;
import com.ct.webDemo.common.entity.Product;
import com.github.pagehelper.PageInfo;

public interface DemoService extends BaseService<Product, Integer> {
	
	//取数据总条数
	public int getDataTotalNum ();
	
	//取全部数据，含字段名
	public List<Map<String, Object>> getAllDataAndFields();  
	
	//取全部数据，不含字段名
	public List<Product> getAllProduct();  
	
	//获取列名
	public List<String> getTableFields();
	
	//获取列名转化为bean后的属性名
	public List<String> getTablePropertiesFromXml(String filePath);
	
	//从xml配置文件获取列名
	public List<String> getTableFieldsFromXml(String filePath);
	
	
	/**
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	//public PageInfo<Product> pageProduct(int pageNum, int pageSize);
	
}
