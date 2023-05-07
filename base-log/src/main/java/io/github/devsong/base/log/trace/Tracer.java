package io.github.devsong.base.log.trace;

import lombok.Data;

/**
 * @author zhisong.guan
 */
@Data
public class Tracer {

    private String traceId;

    private String spanId;

    private TraceExtend traceExtend;

    public Tracer() {}

    public Tracer(String traceId) {
        this.traceId = traceId;
    }

    public Tracer(String traceId, String spanId) {
        this.traceId = traceId;
        this.spanId = spanId;
    }

    public Tracer(String traceId, String spanId, TraceExtend traceExtend) {
        this.traceId = traceId;
        this.spanId = spanId;
        this.traceExtend = traceExtend;
    }

    public static Tracer build(String traceId, String spanId) {
        return new Tracer(traceId, spanId);
    }

    public static Tracer build(String traceId, String spanId, TraceExtend traceExtend) {
        return new Tracer(traceId, spanId, traceExtend);
    }
}
