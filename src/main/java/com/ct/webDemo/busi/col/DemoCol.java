package com.ct.webDemo.busi.col;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ct.webDemo.common.entity.Car;
import com.ct.webDemo.common.mapper.CarMapper;
import com.ct.webDemo.util.LoggerTest;


@Controller
@RequestMapping("/col")
public class DemoCol {
    @Autowired
    CarMapper carMapper;
    
    Logger logger = LoggerFactory.getLogger(DemoCol.class);
    
    @RequestMapping("/list")
    public ModelAndView listall(HttpServletRequest request,HttpServletResponse response){
        List<Car> cars=carMapper.selectAll();
        logger.info("list cars's size is : " + String.valueOf(cars.size()));
        ModelAndView mav=new ModelAndView("list");
        mav.addObject("cars",cars);
        return mav;
    }
}
