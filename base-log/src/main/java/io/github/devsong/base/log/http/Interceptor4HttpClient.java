package io.github.devsong.base.log.http;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.*;
import org.apache.http.client.methods.HttpUriRequest;
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
public class Interceptor4HttpClient {
    static final String START_REQUEST_TIME = "START_REQUEST_TIME";
    static final String HOST = "HOST";
    static final String PATH = "PATH";
    static final String METHOD = "METHOD";

    public static class Interceptor4HttpClientRequest implements HttpRequestInterceptor {
        @Override
        public void process(HttpRequest request, HttpContext context) throws HttpException, IOException {
            Map<String, String> map = Optional.ofNullable(MDC.getCopyOfContextMap()).orElse(newHashMap());

            String traceId = map.get(TRACE_ID);
            // 当前线程调用中有traceId，则将该traceId进行透传
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
            long start = System.currentTimeMillis();
            context.setAttribute(START_REQUEST_TIME, start);
            if (request instanceof HttpUriRequest) {
                HttpUriRequest httpUriRequest = (HttpUriRequest) request;
                java.net.URI uri = httpUriRequest.getURI();
                context.setAttribute(HOST, uri.getHost());
                context.setAttribute(PATH, uri.getPath());
                context.setAttribute(METHOD, httpUriRequest.getMethod());
            }
        }
    }

    @Slf4j
    public static class Interceptor4HttpClientResponse implements HttpResponseInterceptor {
        @Override
        public void process(HttpResponse response, HttpContext context) throws HttpException, IOException {
            Object obj = context.getAttribute(START_REQUEST_TIME);
            if (obj == null) {
                return;
            }
            try {
                long start = (Long) obj;
                long elapsed = System.currentTimeMillis() - start;
                String host = context.getAttribute(HOST) == null ? "" : context.getAttribute(HOST).toString();
                String path = context.getAttribute(PATH) == null ? "" : context.getAttribute(PATH).toString();
                String method = context.getAttribute(METHOD) == null ? "" : context.getAttribute(METHOD).toString();
                HttpLogUtil.recordHttpPerfLog(host, path, method, response.getStatusLine().getStatusCode(), elapsed);
            } catch (Exception e) {
                log.error("record http client perf log error", e);
            }
        }
    }
}
