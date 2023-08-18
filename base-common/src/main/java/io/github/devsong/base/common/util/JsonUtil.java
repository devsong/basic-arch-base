package io.github.devsong.base.common.util;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Getter;
import lombok.SneakyThrows;

import java.util.List;
import java.util.Map;

public class JsonUtil {
    @Getter
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private static final TypeReference<Map<String, Object>> MAP_REF = new TypeReference<>() {
    };

    static {
        // 忽略未识别的属性
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //
        OBJECT_MAPPER.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);
        //
        OBJECT_MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        OBJECT_MAPPER.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        OBJECT_MAPPER.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        OBJECT_MAPPER.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        // 输出非空字段
        OBJECT_MAPPER.setSerializationInclusion(Include.NON_NULL);
        OBJECT_MAPPER.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
        OBJECT_MAPPER.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
//         OBJECT_MAPPER.activateDefaultTyping(ptv, ObjectMapper.DefaultTyping.NON_FINAL);
    }

    /**
     * 对象到json
     *
     * @param object
     * @return
     */
    @SneakyThrows
    public static String toJSONString(Object object) {
        return OBJECT_MAPPER.writeValueAsString(object);
    }

    /**
     * json到对象
     *
     * @param <T>
     * @param str
     * @param clazz
     * @return
     */
    @SneakyThrows
    public static <T> T parseObject(String str, Class<T> clazz) {
        return OBJECT_MAPPER.readValue(str, clazz);
    }

    /**
     * json到对象
     *
     * @param str
     * @param ref
     * @param <T>
     * @return
     */
    @SneakyThrows
    public static <T> T parseObject(String str, TypeReference<T> ref) {
        return OBJECT_MAPPER.readValue(str, ref);
    }

    /**
     * 序列化列表
     *
     * @param <T>
     * @param json
     * @param clazz
     * @return
     */
    @SneakyThrows
    public static <T> List<T> parseList(String json, Class<T> clazz) {
        JavaType javaType = OBJECT_MAPPER.getTypeFactory().constructParametricType(List.class, clazz);
        return OBJECT_MAPPER.readValue(json, javaType);
    }

    @SneakyThrows
    public static JsonNode parseJson(String json) {
        return OBJECT_MAPPER.readTree(json);
    }

    public static Map<String, Object> bean2Map(Object obj) {
        return OBJECT_MAPPER.convertValue(obj, MAP_REF);
    }
}
