/**
 * @FileName: JsonEntityTransform.java
 * @Package com.asura.framework.entity
 * 
 * @author zhangshaobin
 * @created 2012-11-14 下午2:16:43
 * 
 * Copyright 2011-2015 asura
 */
package com.eric.demo.commons.validator;


import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>Json与Entity互相转化</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * <PRE>
 * 
 * @author zhangshaobin
 * @since 1.0
 * @version 1.0
 */
public class JsonEntityTransform {
    public static final Logger LOGGER = LoggerFactory.getLogger(JsonEntityTransform.class);
    
	/**
	 * 
	 * 将json转换成Entity
	 *
	 * @author zhangshaobin
	 * @created 2012-11-14 下午2:19:43
	 *
	 * @param json	要转换的json数据
	 * @param clazz	目标实体
	 * @return	Entity	转换后的实体对象
	 */
	public static <T extends BaseEntity> T json2Entity(String json, Class<T> clazz) throws BusinessException {
		ObjectMapper mapper = new ObjectMapper();
		T entity = null;
		try {
			entity = mapper.readValue(json, clazz);
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException("Json转换成Entity时出现异常。", e);
		} 
		return entity;
	}
	
	/**
	 * 
	 * 将json转换成Object
	 *
	 * @author zhangshaobin
	 * @created 2012-11-14 下午2:19:43
	 *
	 * @param json	要转换的json数据
	 * @param clazz	目标实体
	 * @return	Entity	转换后的实体对象
	 * @throws BusinessException	类型转换异常
	 */
	public static <T> T json2Object(String json, Class<T> clazz) throws BusinessException {
		ObjectMapper mapper = new ObjectMapper();
		T t = null;
		try {
			t = mapper.readValue(json, clazz);
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException("Json转换成Object时出现异常。", e);
		} 
		return t;
	}
	
	
	
	/**
	 * 
	 * 将json转换成DataTransferObject
	 *
	 * @author zhangshaobin
	 * @created 2012-11-14 下午2:19:43
	 *
	 * @param json	要转换的json数据
	 * @return	DataTransferObject	转换后的实体对象
	 * @throws BusinessException	类型转换异常
	 */
	public static DataTransferObject json2DataTransferObject(String json) throws BusinessException {
		ObjectMapper mapper = new ObjectMapper();
		DataTransferObject dto = null;
		try {
			dto = mapper.readValue(json, DataTransferObject.class);
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException("Json转换成DataTransferObject时出现异常。", e);
		} 
		return dto;
	}
	

	/**
	 * 
	 * 将Json转换成List类型集合
	 *
	 * @author zhangshaobin
	 * @created 2013-4-22 下午1:37:13
	 *
	 * @param json	要转换的json数据
	 * @param clazz	转换后的实体类型
	 * @return	转换后的实体集合
	 * @throws BusinessException	类型转换异常
	 */
	@SuppressWarnings("unchecked")
	public static <T extends BaseEntity> List<T>  json2List(String json, Class<T> clazz) throws BusinessException {
		ObjectMapper mapper = new ObjectMapper();
		JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, clazz);
		try {
			return (List<T>)mapper.readValue(json, javaType);
		} catch (JsonParseException e) {
			e.printStackTrace();
			throw new BusinessException("Json转换成List<T>异常。", e);
		} catch (JsonMappingException e) {
			e.printStackTrace();
			throw new BusinessException("Json转换成List<T>异常。", e);
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException("Json转换成List<T>异常。", e);
		}
	}
	
	/**
	 * 
	 * 将包含Entity的数据传输对象转换成Json
	 *
	 * @author zhangshaobin
	 * @created 2012-11-14 下午2:29:53
	 *
	 * @return	json	转换后的json数据
	 * @throws BusinessException	类型转换异常
	 */
	public static String entity2Json(DataTransferObject dto) throws BusinessException {
		ObjectMapper mapper = new ObjectMapper();
		StringWriter writer = new StringWriter();
		try {
			mapper.writeValue(writer, dto);
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException("Entity转换成Json时出现异常。", e);
		}
		return writer.toString();
	}
	
	/**
	 * 
	 * 将json转换成String
	 *
	 * @author zhangshaobin
	 * @created 2012-12-3 下午2:26:38
	 *
	 * @param json	json数据
	 * @return	String	转换后的字符
	 * @throws BusinessException	类型转换异常
	 */
	public static String json2String(String json) throws BusinessException {
		ObjectMapper mapper = new ObjectMapper();
		String result = null;
		try {
			result = mapper.readValue(json, String.class);
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException("Json转换成String时出现异常。", e);
		} 
		return result;
	}
	
	/**
	 * 
	 * 将json数据转换成Map
	 *
	 * @author zhangshaobin
	 * @created 2012-12-3 下午2:35:01
	 *
	 * @param json	要转换的json数据
	 * @return	Map 转换后的结果
	 * @throws BusinessException	类型转换异常
	 */
	public static Map<?, ?> json2Map(String json) throws BusinessException {
		ObjectMapper mapper = new ObjectMapper();
		Map<?, ?> result = null;
		try {
			result = mapper.readValue(json, Map.class);
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException("Json转换成Map时出现异常。", e);
		} 
		return result;
	}
	/**
	 * 
	 * 将自定义对象转json
	 *
	 * @author bushujie
	 * @created 2012-11-14 下午2:29:53
	 *
	 * @return	json	转换后的json数据
	 * @throws BusinessException	类型转换异常
	 */
	public static String Object2Json(Object o) throws BusinessException {
		ObjectMapper mapper = new ObjectMapper();
		StringWriter writer = new StringWriter();
		try {
			mapper.writeValue(writer, o);
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException("Entity转换成Json时出现异常。", e);
		}
		return writer.toString();
	}

		
}
