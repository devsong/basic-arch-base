package io.github.devsong.base.log;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.devsong.base.log.trace.*;
import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;

/**
 * 基于servlet filter实现
 * date:  2023/5/29
 * author:guanzhisong
 */
public class TracingFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        try {
            initTraceContextAndMdc((HttpServletRequest) request);
            chain.doFilter(request, response);
        } finally {
            TraceContext.remove();
            MDC.remove(TraceConstants.TRACE_ID);
            MDC.remove(TraceConstants.SPAN_ID);
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

    private static void initTraceContextAndMdc(HttpServletRequest request) throws JsonProcessingException {
        String traceId = request.getHeader(TraceConstants.TRACE_ID);
        if (StringUtils.isBlank(traceId)) {
            traceId = TraceIdGenerator.getTraceId(IdGenEnum.UUID, "");
        }
        String spanId = request.getHeader(TraceConstants.SPAN_ID);
        if (StringUtils.isBlank(spanId)) {
            spanId = TraceIdGenerator.getSpanId();
        } else {
            spanId = spanId + TraceConstants.SPAN_SEPARATOR + TraceIdGenerator.getSpanId();
        }
        String extend = request.getHeader(TraceConstants.TRACE_EXTEND);
        TraceExtend traceExtend = null;

        if (StringUtils.isNotBlank(extend)) {
            traceExtend = JacksonUtil.mapper().readValue(extend, TraceExtend.class);
        }

        Tracer tracer = Tracer.build(traceId, spanId, traceExtend);
        TraceContext.set(tracer);
        MDC.put(TraceConstants.TRACE_ID, traceId);
        MDC.put(TraceConstants.SPAN_ID, spanId);
    }
}
