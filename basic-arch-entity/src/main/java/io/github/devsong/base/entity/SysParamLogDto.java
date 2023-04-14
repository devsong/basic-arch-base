package io.github.devsong.base.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhisong.guan
 * @date 2022/10/10 18:14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SysParamLogDto {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * traceId
     */
    private String traceId;
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
     * 入参
     */
    private String paramsIn;
    /**
     * 出参
     */
    private String paramsOut;
}
