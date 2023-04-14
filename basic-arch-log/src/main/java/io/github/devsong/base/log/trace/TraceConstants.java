package io.github.devsong.base.log.trace;

/**
 * @author zhisong.guan
 */
public class TraceConstants {
    /**
     * traceId, 链路追踪,一个trace有多个span构成
     */
    public static final String TRACE_ID = "traceId";

    /**
     * 链路调用段,span代表一个服务内
     */
    public static final String SPAN_ID = "spanId";

    /**
     * trace 扩展数据
     */
    public static final String TRACE_EXTEND = "traceExtend";

    public static final String SPAN_SEPARATOR = ".";


}
