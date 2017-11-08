package com.group.project.util;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * @Description:   读取配置文件的工具类
 */
public class PropUtil {

	private static Logger logger = Logger.getLogger(PropUtil.class);
	
	public static Properties propsOfConfig = new Properties();
	
	static {
		//文件在class的根路径  
        InputStream is=PropUtil.class.getClassLoader().getResourceAsStream("config.properties");
        BufferedReader br= new BufferedReader(new InputStreamReader(is));
        try {
        	propsOfConfig.load(br);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	public static String getString(String key) {
		return propsOfConfig.getProperty(key);
	}
	
	public static int getInt(String key) {
		int result = 0 ;
		try{
			result =  Integer.parseInt(propsOfConfig.getProperty(key));
		}catch(Exception e){
			logger.error("throw exception when parsing string to integer in PropUtil");
		}
		return result;
	}

	public static boolean getBool(String key) {
		boolean result = false;
		try {
			result = Boolean.parseBoolean(propsOfConfig.getProperty(key));
		} catch (Exception e) {
			logger.error("throw exception when parsing string to boolean in PropUtil");
		}
		return result;
	}
	
}
