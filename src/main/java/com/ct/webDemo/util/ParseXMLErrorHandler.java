package com.ct.webDemo.util;

import org.xml.sax.ErrorHandler;  
import org.xml.sax.SAXException;  
import org.xml.sax.SAXParseException;  
  
public class ParseXMLErrorHandler implements ErrorHandler {  
  
    /* 
     * 接收可恢复的错误的通知 
     */  
    @Override  
    public void error(SAXParseException e) throws SAXException {  
        System.out.println("Error ("+e.getLineNumber()+","  
                +e.getColumnNumber()+") : "+e.getMessage());  
    }  
      
    /* 
     * 接收不可恢复的错误的通知。 
     */  
    @Override  
    public void fatalError(SAXParseException e) throws SAXException {  
        System.out.println("FatalError ("+e.getLineNumber()+","  
                +e.getColumnNumber()+") : "+e.getMessage());  
    }  
  
    @Override  
    public void warning(SAXParseException e) throws SAXException {  
        System.out.println("Warning ("+e.getLineNumber()+","  
                +e.getColumnNumber()+") : "+e.getMessage());  
    }  
  
}  