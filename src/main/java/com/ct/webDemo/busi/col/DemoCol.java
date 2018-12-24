package com.ct.webDemo.busi.col;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.ct.webDemo.busi.service.DemoService;
import com.ct.webDemo.common.entity.Car;
import com.ct.webDemo.common.entity.Product;
import com.ct.webDemo.common.mapper.CarMapper;
import com.ct.webDemo.common.mapper.ProductMapper;


@Controller
@RequestMapping("/col")
public class DemoCol {  
    @Autowired
    CarMapper carMapper;
    @Autowired
    ProductMapper productMapper;
    @Autowired
	private DemoService demoService;
    
    private static final Logger logger = LoggerFactory.getLogger(DemoCol.class);
    
    @RequestMapping("/list")
    public ModelAndView listAll(HttpServletRequest request,HttpServletResponse response){
        List<Car> cars=carMapper.selectAll();
        logger.info("list cars's size is : " + String.valueOf(cars.size()));
        ModelAndView mav=new ModelAndView("list");
        mav.addObject("cars",cars);
        return mav;
    }
    
    @RequestMapping("/car")
    public String getAllCar(ModelMap map,HttpServletRequest request){
        List<Car> cars=carMapper.selectAll();
        logger.info("list cars's size is : " + String.valueOf(cars.size()));
        map.put("car", cars);
        return "success get all car!";
    }
    
    @RequestMapping("/allProduct")
    public ModelAndView allProduct(HttpServletRequest request){
    	List<List<Object>> allList = new ArrayList<List<Object>>(); 
    	List<Map<String, Object>> map = demoService.getAllDataAndFields();  
    	int index = 0;  
    	for (Map<String, Object> kv : map) {  
    	    List<Object> key = new ArrayList<Object>();  
    	    List<Object> value = new ArrayList<Object>();  
    	    for (Map.Entry<String, Object> entry : kv.entrySet()) {  
    	        if (index == 0) {   
    	            key.add(entry.getKey());  
    	        }  
    	        value.add(entry.getValue());  
    	    }  
    	    if (index == 0) {  
    	        allList.add(key);  
    	    }  
    	    allList.add(value);  
    	    index++;  
    	}  
    	
    	logger.info(JSONObject.toJSONString(allList));
        ModelAndView mav=new ModelAndView("allProduct");
        return mav;
    }
    
    
    @RequestMapping(value = "/productById", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getProduct(HttpServletRequest request, @RequestBody String str,
                         @RequestParam(value = "id") String id) {	
        Product product = productMapper.selectByPrimaryKey(Integer.parseInt(id));
        logger.info("In contorller : " + product.toString());
        return product.toString();
    }
    
    
    
}
