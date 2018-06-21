package com.ct.webDemo.test.util;

import java.io.FileNotFoundException;

import org.junit.runners.model.InitializationError;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Log4jConfigurer;

/* spring与log4j的整合，是放在web.xml里的，随tomcat启动后，spring才会加载log4j，
 * 而用junit测试是不需要tomcat启动的，所以Junit与log4j的整合才比较费劲。Junit使
 * 用spring时，若spring没加载到log4j就会报警告。
 * 
 * Junit加载spring的runner（SpringJUnit4ClassRunner）要优先于spring加载log4j，
 * 因此采用普通方法，无法实现spring先加载log4j后被Junit加载。所以我们需要新建JUnit4ClassRunner类，
 * 修改SpringJUnit4ClassRunner加载log4j的策略。这样加载log4j就会优先于加载spring了。
*/public class JUnit4ClassRunner extends SpringJUnit4ClassRunner {  
  
    static {  
        try {  
            Log4jConfigurer.initLogging("classpath:properties/log4j.properties");  
        } catch (FileNotFoundException ex) {  
            System.err.println("Cannot Initialize log4j");  
        }  
    }   
      
    public JUnit4ClassRunner(Class<?> clazz) throws InitializationError {  
        super(clazz);  
    }  
  
}  