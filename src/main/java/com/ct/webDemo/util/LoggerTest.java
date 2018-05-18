package com.ct.webDemo.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerTest {
    public static void main(String[] args) {
        String name = "hzh";
        Logger logger = LoggerFactory.getLogger(LoggerTest.class);
        logger.debug("Hello World");
        logger.info("hello {}", name);
    }
}