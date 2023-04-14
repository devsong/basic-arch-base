package io.github.devsong.base.log.trace;

import com.alibaba.ttl.TransmittableThreadLocal;
import org.slf4j.MDC;

import static io.github.devsong.base.log.trace.TraceConstants.SPAN_ID;
import static io.github.devsong.base.log.trace.TraceConstants.TRACE_ID;


/**
 * @author zhisong.guan
 */
public class TraceContext {

    private static final ThreadLocal<Tracer> TRACER_THREAD_LOCAL = new TransmittableThreadLocal<>();

    public static void set(Tracer tracer) {
        TRACER_THREAD_LOCAL.set(tracer);
    }

    public static Tracer get() {
        return TRACER_THREAD_LOCAL.get();
    }

    public static void remove() {
        TRACER_THREAD_LOCAL.remove();
    }

    public static void init(IdGenEnum idGenEnum, String prefix) {
        Tracer tracer = Tracer.build(TraceIdGenerator.getTraceId(idGenEnum, prefix), "0");
        set(tracer);
        MDC.put(TRACE_ID, tracer.getTraceId());
        MDC.put(SPAN_ID, tracer.getSpanId());
    }

}
