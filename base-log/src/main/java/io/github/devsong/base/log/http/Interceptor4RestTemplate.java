package io.github.devsong.base.log.http;

import static com.google.common.collect.Maps.newHashMap;
import static io.github.devsong.base.log.trace.TraceConstants.*;

import com.google.common.base.Stopwatch;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

/**
 * @author zhisong.guan
 * @date 2022/10/15 10:51
 */
public class Interceptor4RestTemplate implements ClientHttpRequestInterceptor {
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
            throws IOException {
        Map<String, String> map = Optional.ofNullable(MDC.getCopyOfContextMap()).orElse(newHashMap());

        String traceId = map.getOrDefault(TRACE_ID, "");
        // 当前线程调用中有traceId，则将该traceId进行透传
        if (StringUtils.isNotBlank(traceId)) {
            request.getHeaders().add(TRACE_ID, traceId);
        }
        String spanId = map.getOrDefault(SPAN_ID, "");
        if (StringUtils.isNotBlank(spanId)) {
            request.getHeaders().add(SPAN_ID, spanId);
        }
        String traceExtend = map.getOrDefault(TRACE_EXTEND, "");
        if (StringUtils.isNotBlank(traceExtend)) {
            request.getHeaders().add(TRACE_EXTEND, traceExtend);
        }
        Stopwatch stopwatch = Stopwatch.createStarted();
        ClientHttpResponse resp = null;
        java.net.URI uri = request.getURI();
        try {
            resp = execution.execute(request, body);
        } finally {
            long elapsed = stopwatch.stop().elapsed(TimeUnit.MILLISECONDS);
            HttpLogUtil.recordHttpPerfLog(
                    uri.getHost(),
                    uri.getPath(),
                    request.getMethodValue(),
                    resp == null?-1: resp.getStatusCode().value(),
                    elapsed);
        }
        return resp;
    }
}
