package io.github.devsong.base.log.http;

import static com.google.common.collect.Maps.newHashMap;
import static io.github.devsong.base.log.trace.TraceConstants.*;

import com.google.common.base.Stopwatch;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;

/**
 * @author zhisong.guan
 * @date 2022/10/15 10:51
 */
public class Interceptor4OkHttp implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Map<String, String> map = Optional.ofNullable(MDC.getCopyOfContextMap()).orElse(newHashMap());

        String traceId = map.getOrDefault(TRACE_ID, "");
        String spanId = map.getOrDefault(SPAN_ID, "");
        String traceExtend = map.getOrDefault(TRACE_EXTEND, "");
        Request.Builder builder = chain.request().newBuilder();
        if (StringUtils.isNotBlank(traceId)) {
            builder.addHeader(TRACE_ID, traceId);
        }
        if (StringUtils.isNotBlank(spanId)) {
            builder.addHeader(SPAN_ID, spanId);
        }
        if (StringUtils.isNotBlank(traceExtend)) {
            builder.addHeader(TRACE_EXTEND, traceExtend);
        }
        Request request = builder.build();
        java.net.URI uri = request.url().uri();
        Response resp = null;
        Stopwatch stopwatch = Stopwatch.createStarted();
        try {
            resp = chain.proceed(request);
        } finally {
            stopwatch.stop();
            long elapsed = stopwatch.elapsed(TimeUnit.MILLISECONDS);
            HttpLogUtil.recordHttpPerfLog(
                    uri.getHost(), uri.getPath(), request.method(), resp == null ? -1 : resp.code(), elapsed);
        }
        return resp;
    }
}
