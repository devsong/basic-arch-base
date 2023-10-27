package io.github.devsong.base.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author guanzhisong
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SysPerfHttpLogDto implements Serializable {
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
     * 请求域名
     */
    private String host;
    /**
     * url
     */
    private String url;
    /**
     * http method
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
     * 创建时间
     */
    private LocalDateTime createTime;
}
