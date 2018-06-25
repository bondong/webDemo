package com.ct.webDemo.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @ClassName: FileUtils
 * @Description: 文件操作类，扫描、转移文件
 */
@SuppressWarnings("all")
public class FileUtils  {

	public static final String EMPTY = "";  
    public static final String POINT = ".";  
    
    private static Logger logger = LoggerFactory.getLogger(FileUtils.class);
    private static String fileUploadDir;
    private static String loadInDBDir;
    List<String> fileNames = new ArrayList<String>();
    
    static {
        Properties properties = new Properties();
        try {
        	//绝对路径
            properties.load(FileUtils.class.getResourceAsStream("/properties/config.properties"));
            fileUploadDir = properties.getProperty("fileUploadDir");
            loadInDBDir = properties.getProperty("loadInDBDir");
            logger.info("fileUploadDir:" + fileUploadDir);
        } catch(IOException e) {
            e.printStackTrace();
        }

    }
    
    /**
     * @Description: 获取接口文件目录
     */
    public List<String> fileList() {
    	List<String> fileNames = new ArrayList<String>();
        if (!isExist(fileUploadDir)) {
            logger.info("The file path " + fileUploadDir + " does not exists.");
        } else {
            logger.info("The current scanning directory: " + fileUploadDir);
            fileNames = fileList(fileUploadDir ,false);
        }
        return fileNames;
    }

    /**
     * @Description: 获取文件目录下的文件列表
     * @param rootStr
     */
    public List<String> fileList(String rootStr,boolean recursionFlag) {
    	
    	List<String> fileNames = new ArrayList<String>();
    	
        File root = new File(rootStr);
        File[] file = root.listFiles();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        //可能会使用的文件属性
        String id = null; // UUID
        String opTime; // 本次操作日期
        String interFileName; // 源文件名
        String sourceDir = null; // 文件路径
        String fileSize; // 文件大小
        String updateTime; // 文件最后修改时间

        for (File f : file) {
            interFileName = f.getName(); // 获取文件名

            id = UUID.randomUUID().toString().replace("-", ""); // 获取ID, Unique
            sourceDir = f.getAbsolutePath().toString().replace("\\", "/");

            if (f.isFile()) {
            	
            	fileSize = Long.toString(f.length()); // 获取文件大小
                updateTime = sdf.format(new Date(f.lastModified())); // 获取文件最后修改时间
                //opTime = getOpTime(interFileName); // 获取操作时间

                if (interFileName.endsWith(".dat") || interFileName.endsWith(".DAT")) {//数据文件
                    
                } else if (interFileName.endsWith(".chk") || interFileName.endsWith(".CHK")) {//校验文件
                    
                } else if (interFileName.endsWith(".xlsx") || interFileName.endsWith(".XLSX")
                		||interFileName.endsWith(".xls") || interFileName.endsWith(".XLS")){	
                	logger.info("The office xls file :" + interFileName );
                	//移动文件到上传目录，正在上传数据被占用的文件跳过
                	try {
                		moveFile(loadInDBDir,f);
                		fileNames.add(interFileName);
                		//moveFile(fileUploadDir,loadInDBDir,interFileName);
                	}catch (Exception e) {
                		e.printStackTrace();
                		continue;
                	}
                } else {
                    logger.info("The file : " + sourceDir + " is illegal, pass it.");
                }
            } else if (f.isDirectory()) {
            	//递归调用，此处禁止
            	if(recursionFlag) {
            		fileList(f.toString(),recursionFlag);
            	}
            }

        }
        return fileNames;
    }
    
    /**
     * @Description: 移动文件
     * @param targetPath 目标文件夹
     * @param File 文件
     */
    public void moveFile (String targetPath ,File f) throws Exception {
    	f.renameTo(new File(unifiedSeparator(targetPath) + f.getName()));
    }
    
    /**
     * @Description: 移动文件
     * @param originalPath 源文件夹
     * @param targetPath 目标文件夹
     * @param fileName 文件名
     */
    public void moveFile (String originalPath,String targetPath,String fileName) throws Exception {
	    String totalOPath = unifiedSeparator(originalPath) + fileName;
        File startFile = new File(totalOPath);
        File tmpFile = new File(targetPath);//获取文件夹路径
        if(!tmpFile.exists()){//判断文件夹是否创建，没有创建则创建新文件夹
            tmpFile.mkdirs();
        }
        startFile.renameTo(new File(unifiedSeparator(targetPath) + fileName));
    }
    /**
     * @Description: 判断文件目录是否存在
     * @param filePath
     * @return boolean 返回类型
     */
    public static boolean isExist(String filePath) {
        boolean exists = true;
        File file = new File(filePath);
        if (!file.exists()) {
            exists = false;
        }
        return exists;
    }
    
    /** 
     * 去掉文件的后缀名 
     * @param path 
     * @return 
     */  
    public static String getFilePrefix(String fileName){  
        if(null ==fileName || EMPTY.equals(fileName.trim())){  
            return EMPTY;  
        }  
        if(fileName.contains(POINT)){  
            return fileName.substring(0,fileName.lastIndexOf(POINT));  
        }  
        return EMPTY;  
    }
    
    /** 
     * 获得文件的后缀名 
     * @param path 
     * @return 
     */  
    public static String getPostfix(String path){  
        if(path==null || EMPTY.equals(path.trim())){  
            return EMPTY;  
        }  
        if(path.contains(POINT)){  
            return path.substring(path.lastIndexOf(POINT)+1,path.length());  
        }  
        return EMPTY;  
    } 
    
    /**
     * 
     * @Description: 获取文件路径，统一格式全部都以分隔符结束
     * @param interDirectory
     * @return String 返回类型
     */
    public static String unifiedSeparator(String interDirectory) {
        String separator = "/";

        if (interDirectory.endsWith(separator)) {
            return interDirectory;
        } else {
            return interDirectory + separator;
        }
    }

    /**
     * @Title: getOpTime
     * @Description: 判断是日接口还是月接口，并获取相应的日期; 
     * 月接口：例M24289201408231155 非月接口：A/I/P，I0606920140820230045
     * @param interDirectory
     * @return String 返回类型
     */
    public static String getOpTime(String interDirectory) {
        String opTimeString = null;
        try {
            if ("M".equalsIgnoreCase(interDirectory.substring(0, 1))) {
                opTimeString = interDirectory.substring(6, 12);
            } else {
                opTimeString = interDirectory.substring(6, 14);
            }
        } catch (Exception e) {
            logger.info("Failure to obtain the operating time: "
                    + e.getMessage());
        }
        return opTimeString;
    }
    
    
    public static void main( String args[] ) throws Exception {  
    	FileUtils f = new FileUtils();
    	f.fileList();
    }
    

}