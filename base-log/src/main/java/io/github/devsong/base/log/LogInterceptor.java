package io.github.devsong.base.log;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.devsong.base.log.trace.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @author zhisong.guan
 * @date 2022/10/15 10:52
 */
@Slf4j
public class LogInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        initTraceContextAndMdc(request);
        return true;
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

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        // 调用结束后删除
        try {
            TraceContext.remove();
            MDC.remove(TraceConstants.TRACE_ID);
            MDC.remove(TraceConstants.SPAN_ID);
        } catch (Exception e) {
            log.error("error remove mdc context");
        }
    }
}
