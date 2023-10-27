package io.github.devsong.base.entity.annotation;

import io.github.devsong.base.entity.enums.RwOperationEnum;
import java.lang.annotation.*;

/**
 * 接口读写操作类型,仅作为标记注解,便于规划接口级别读写类型,不对业务接口产生实质性影响
 *
 * @author zhisong.guan
 * @date 2022/9/30 16:55
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RwOperationType {
    RwOperationEnum value();
}
