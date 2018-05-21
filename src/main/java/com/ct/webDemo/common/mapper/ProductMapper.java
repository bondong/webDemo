package com.ct.webDemo.common.mapper;

import java.util.List;
import java.util.Map;

import com.ct.webDemo.base.CommonMapper;
import com.ct.webDemo.common.entity.Product;

public interface ProductMapper extends CommonMapper<Product> {
	
	public List<Map<String, Object>> getAllProduct();  
	
}