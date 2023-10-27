package io.github.devsong.base.log.trace;

/**
 * @author zhisong.guan
 */
public interface TraceConstants {
    /**
     * traceId, 链路追踪,一个trace有多个span构成
     */
    String TRACE_ID = "traceId";

    /**
     * 链路调用段,span代表一个服务内
     */
    String SPAN_ID = "spanId";

    /**
     * trace 扩展数据
     */
    String TRACE_EXTEND = "traceExtend";

    String SPAN_SEPARATOR = ".";

    String URI = "uri";
}
