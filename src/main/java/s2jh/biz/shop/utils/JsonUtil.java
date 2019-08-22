package s2jh.biz.shop.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/** 
 * Project Name:s2jh4net 
 * File JsonUtil.java 
 * Package Name:s2jh.biz.shop.utils.JsonUtil
 * Date:2017年3月16日下午5:55:39 
 * Copyright (c) 2017,  All Rights Reserved. 
 * author zlp
*/  
public class JsonUtil {
	private JsonUtil(){
	}
	public static final ObjectMapper OM = new ObjectMapper();
	static{
		// 设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性  
		OM.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		OM.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		OM.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
	}
	/**
	 * 
	 * @Title: assignList 
	 * @Description: 得到 一个 集合嵌套某个类的JavaType
	 * @param collection
	 * @param clazz
	 * @return JavaType
	 */
	public static JavaType assignList(@SuppressWarnings("rawtypes") Class<? extends Collection> collection, Class<? extends Object> clazz) {
		try {
			return JsonUtil.OM.getTypeFactory().constructParametricType(collection, clazz);
		} catch (Exception e) {
			throw new JsonUtilException(e);
		}
	}
	/**
	 * 
	 * @Title: readValuesAsArrayList 
	 * @Description:  JSON字符串转为List内包含  参数 clazz
	 * @param json
	 * @param clazz
	 * @return ArrayList<T>
	 */
	public static <T> List<T> readValuesAsArrayList(String json, Class<T> clazz) {
		ArrayList<T> list = null;
		try {
			list = OM.readValue(json, assignList(ArrayList.class, clazz));
		} catch (Exception e) {
			throw new JsonUtilException(e);
		}
		return list;
	}
	/**
	 * 
	 * @Title: toJson 
	 * @Description: 对象转为JSON字符串
	 * @param obj
	 * @return String
	 */
	public static String toJson(Object obj){
		try {
			return OM.writeValueAsString(obj);
		} catch (Exception e) {
			throw new JsonUtilException(e);
		}
	}
	/**
	 * 
	 * @Title: fromJson 
	 * @Description:  JSON字符串转为clazz类型对象
	 * @param json
	 * @param clazz
	 * @return T
	 */
	public static <T> T fromJson(String json, Class<T> clazz){
		try {
			return OM.readValue(json, clazz);
		} catch (Exception e) {
			throw new JsonUtilException(e);
		}
	}
	/**
	 * 
	 * @ClassName: JsonUtilException 
	 * @Description: Json转换异常 
	 * @author kangjie kangjie_litsoft_com_cn
	 * @date 2016年7月4日 上午11:31:38 
	 */
	public static class JsonUtilException extends RuntimeException {
		private static final long serialVersionUID = -4876354346591087636L;
		private final Throwable e;
		private JsonUtilException(Throwable e) {
			this.e = e;
		}
		@Override
		public String getMessage() {
			return e.getMessage();
		}
		@Override
		public void printStackTrace() {
			e.printStackTrace();
		}
		@Override
		public synchronized Throwable getCause() {
			return e.getCause();
		}
		@Override
		public void printStackTrace(PrintStream s) {
			e.printStackTrace(s);
		}
		@Override
		public void printStackTrace(PrintWriter s) {
			e.printStackTrace(s);
		}
		@Override
		public String toString() {
			return e.toString();
		}
		@Override
		public synchronized Throwable initCause(Throwable cause) {
			return e.initCause(cause);
		}
		@Override
		public synchronized Throwable fillInStackTrace() {
			if(e == null){
				return null;
			}
			return e.fillInStackTrace();
		}
		@Override
		public String getLocalizedMessage() {
			return e.getLocalizedMessage();
		}
		@Override
		public StackTraceElement[] getStackTrace() {
			return e.getStackTrace();
		}
		@Override
		public void setStackTrace(StackTraceElement[] stackTrace) {
			e.setStackTrace(stackTrace);
		}
	}
}
