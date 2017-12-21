package com.eric.demo.commons.util;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by zhaoming on 2017/10/18.
 */
public class JsonUtil {

    private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final ObjectMapper objMapper = new ObjectMapper();

    public JsonUtil() {

    }

    public static String toJson(Object obj) {
        String rst = null;
        if(obj != null && !(obj instanceof String)) {
            try {
                rst = objMapper.writeValueAsString(obj);
                return rst;
            } catch (Exception var3) {
                logger.error("将Java对象转换成Json串出错！");
                throw new RuntimeException("将Java对象转换成Json串出错！", var3);
            }
        } else {
            return (String)obj;
        }
    }

    public static <T> T fromJson(String json, Class<T> type) {
        Object rst = null;

        try {
            rst = objMapper.readValue(json, type);
            return (T) rst;
        } catch (Exception var4) {
            logger.error("Json串转换成对象出错：{}", json);
            throw new RuntimeException("Json串转换成对象出错!", var4);
        }
    }

    public static <T> T fromJson(String json, TypeReference<T> typeRef) {
        Object rst = null;

        try {
            rst = objMapper.readValue(json, typeRef);
            return (T) rst;
        } catch (Exception var4) {
            logger.error("Json串转换成对象出错：{}", json);
            throw new RuntimeException("Json串转换成对象出错!", var4);
        }
    }

    public static HashMap<String, Object> fromJsonToMap(String json) {
        HashMap map = new HashMap();

        try {
            map = (HashMap)objMapper.readValue(json, map.getClass());
        } catch (JsonParseException var3) {
            logger.error("Json串转换成对象出错：{}", json);
        } catch (JsonMappingException var4) {
            logger.error("Json串转换成对象出错：{}", json);
        } catch (IOException var5) {
            logger.error("Json串转换成对象出错：{}", json);
        }

        return map;
    }

    public static HashMap<String, Object> toMap(String json) {
        HashMap map = null;

        try {
            map = (HashMap)objMapper.readValue(json, HashMap.class);
        } catch (JsonParseException var3) {
            map = null;
            logger.error("Json串转换成对象出错：{}", json);
        } catch (JsonMappingException var4) {
            map = null;
            logger.error("Json串转换成对象出错：{}", json);
        } catch (IOException var5) {
            map = null;
            logger.error("Json串转换成对象出错：{}", json);
        } catch (Exception var6) {
            map = null;
            logger.error("Json串转换成对象出错：{}", json);
        }

        return map;
    }

    public static List<HashMap<String, Object>> fromJsonToList(String json) {
        List list = null;

        try {
            list = (List)objMapper.readValue(json, List.class);
            return list;
        } catch (JsonParseException var3) {
            logger.error("Json串转换成List出错：{}", json);
            throw new RuntimeException("Json串转换成List出错!", var3);
        } catch (JsonMappingException var4) {
            logger.error("Json串转换成对象出错：{}", json);
            throw new RuntimeException("Json串转换成List出错!", var4);
        } catch (IOException var5) {
            logger.error("Json串转换成对象出错：{}", json);
            throw new RuntimeException("Json串转换成List出错!", var5);
        }
    }

   static {

        //去掉默认的时间戳格式
        objMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        //设置为中国上海时区
        objMapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        objMapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
        //空值不序列化
        objMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        //反序列化时，属性不存在的兼容处理
        objMapper.getDeserializationConfig().withoutFeatures(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        //序列化时，日期的统一格式
        /*objMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));*/
       objMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES,true);
        objMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //单引号处理
        objMapper.configure(com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
    }


}
