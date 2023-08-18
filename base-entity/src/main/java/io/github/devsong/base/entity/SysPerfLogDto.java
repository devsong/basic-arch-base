package io.github.devsong.base.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author guanzhisong
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SysPerfLogDto implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * traceId
     */
    private String traceId;
    /**
     * spanId
     */
    private String spanId;
    /**
     * 产品线
     */
    private String product;
    /**
     * 组名
     */
    private String groupName;
    /**
     * 应用名
     */
    private String app;
    /**
     * 类名
     */
    private String clazz;
    /**
     * 方法名
     */
    private String method;
    /**
     * 实例名
     */
    private String instance;
    /**
     * 执行时间
     */
    private Integer executeTimespan;
    /**
     * 调用结果0:成功,-1:系统异常,大于0业务异常
     */
    private Integer code;
    /**
     * 系统异常堆栈信息
     */
    private String errmsg;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
