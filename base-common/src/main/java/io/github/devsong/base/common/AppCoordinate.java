package io.github.devsong.base.common;

import lombok.Data;

/**
 * @author zhisong.guan
 * @date 2022/10/10 19:25
 */
@Data
public class AppCoordinate {
    /**
     * 产品线
     */
    protected String product;
    /**
     * 分组
     */
    protected String group;
    /**
     * 应用名
     */
    protected String app;
}
