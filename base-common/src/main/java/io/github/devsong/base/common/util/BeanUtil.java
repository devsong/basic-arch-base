package io.github.devsong.base.common.util;

import java.util.List;
import org.springframework.beans.BeanUtils;

/**
 * 对象copy
 * @author guanzhisong
 *
 */
public class BeanUtil extends BeanUtils {

    /**
     * 采用json反序列化的方式
     * @param src
     * @param clazz
     * @param <T>
     * @param <E>
     * @return
     */
    public static <T, E> List<E> copyList(List<T> src, Class<E> clazz) {
        String json = JsonUtil.toJSONString(src);
        return JsonUtil.parseList(json, clazz);
    }
}
