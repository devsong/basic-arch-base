package io.github.devsong.base.log.http;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.protocol.HttpContext;
import org.slf4j.MDC;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import static com.google.common.collect.Maps.newHashMap;
import static io.github.devsong.base.log.trace.TraceConstants.*;

/**
 * @author zhisong.guan
 * @date 2022/10/15 10:51
 */
public class Interceptor4HttpClient implements HttpRequestInterceptor {
    @Override
    public void process(HttpRequest request, HttpContext context) throws HttpException, IOException {
        Map<String, String> map = Optional.ofNullable(MDC.getCopyOfContextMap()).orElse(newHashMap());

        String traceId = map.get(TRACE_ID);
        //当前线程调用中有traceId，则将该traceId进行透传
        if (StringUtils.isNotBlank(traceId)) {
            request.addHeader(TRACE_ID, traceId);
        }
        String spanId = map.get(SPAN_ID);
        if (StringUtils.isNotBlank(spanId)) {
            request.addHeader(SPAN_ID, spanId);
        }
        String traceExtend = map.getOrDefault(TRACE_EXTEND, "");
        if (StringUtils.isNotBlank(traceExtend)) {
            request.addHeader(TRACE_EXTEND, traceExtend);
        }
    }
}
