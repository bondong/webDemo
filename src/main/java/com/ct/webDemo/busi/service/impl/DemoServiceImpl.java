package com.ct.webDemo.busi.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ct.webDemo.base.BaseServiceImpl;
import com.ct.webDemo.busi.service.DemoService;
import com.ct.webDemo.common.entity.Product;
import com.ct.webDemo.common.mapper.ProductMapper;
import com.ct.webDemo.util.ParseXMLUtil;



@Service
public class DemoServiceImpl extends BaseServiceImpl<Product, Integer> implements DemoService {
	@Autowired
	private ProductMapper productMapper;
 
	@Autowired
	public void setProductMapper(ProductMapper productMapper) {
		super.setMapper(productMapper);
		this.productMapper = productMapper;
	}
	
	@Override
	public List<Map<String, Object>> getAllDataAndFields(){
		return productMapper.getAllDataAndFields();
	}
	
	//获取列名
	@Override
	public List<String> getTableFields(){
		
    	List<Map<String, Object>> map = productMapper.getTableFields();  
    	List<String> key = new ArrayList<String>();  
    	if (!map.isEmpty()) {
    		Map<String, Object> kv = map.get(0);
    		for (Map.Entry<String, Object> entry : kv.entrySet()) {
    			key.add(entry.getKey()); 
    		}
    	}
    	return key;
	}
	

	@Override
	public int getDataTotalNum () {
		return 20000;
	}
	
	public List<Product> getAllProduct(){
		return productMapper.getAllProducts();
	}
}