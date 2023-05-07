package io.github.devsong.base.entity.annotation;

import java.lang.annotation.*;

/**
 * 性能日志监控
 *
 * @author guanzhisong
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PerfLog {
    /**
     * 类名
     *
     * @return
     */
    String clazz() default "";

    /**
     * 方法名
     *
     * @return
     */
    String method() default "";

    boolean ignore() default false;
}
