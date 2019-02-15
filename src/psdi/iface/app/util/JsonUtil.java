/*
 * Copyright 2017 SF Airlines Co., Ltd. All rights reserved.
 * 本文件仅限于顺丰航空有限公司内部传阅，禁止外泄以及用于其他的商业目的。
 */

package psdi.iface.app.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import psdi.util.logging.MXLogger;
import psdi.util.logging.MXLoggerFactory;

import java.io.IOException;
import java.util.List;

/**
 * 本类实现的功能写在此处
 * =============================================================
 * Version          Author          Date            Description
 * 1.0              Neal.Y         2017/5/23             创建
 * =============================================================
 */
public class JsonUtil {

    private static ObjectMapper mapper = new ObjectMapper();

    /**
     * 将指定对象转化为JSON字符串
     *
     * @param pojo 对象
     * @param <T>  对象类型
     * @return 转化后的字符串
     */
    public static <T> String toJson(T pojo) throws IOException {

        try {
            return mapper.writeValueAsString(pojo);
        } catch (JsonProcessingException e) {
            MXLogger myLogger = MXLoggerFactory.getLogger("maximo.application");
            myLogger.error(e);
        }
        return "";
    }

    /**
     * 将JSON转化为指定的类型
     *
     * @param json JSON字符串
     * @param type 期望的转化类型
     * @param <T>  类型
     * @return 转化后的对象
     * @throws IOException 输入输出异常
     */
    public static <T> T fromJson(String json, Class<T> type) throws IOException {

        return mapper.readValue(json, type);
    }

    /**
     * 将Json数组转化为List
     *
     * @param json Json字符串表示的数据
     * @param type 期望的转化类型
     * @param <T>  类型
     * @return 转化后的对象
     * @throws IOException 输入输出异常
     */
    public static <T> List<T> fromJsonArray(String json, Class<T> type) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, objectMapper.getTypeFactory().constructCollectionType(List.class, type));
    }
}
