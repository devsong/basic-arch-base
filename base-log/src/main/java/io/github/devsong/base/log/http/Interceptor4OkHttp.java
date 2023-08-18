package io.github.devsong.base.log.http;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;
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
public class Interceptor4OkHttp implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Map<String, String> mdcContextMap =
                Optional.ofNullable(MDC.getCopyOfContextMap()).orElse(newHashMap());

        String traceId = mdcContextMap.getOrDefault(TRACE_ID, "");
        String spanId = mdcContextMap.getOrDefault(SPAN_ID, "");
        String traceExtend = mdcContextMap.getOrDefault(TRACE_EXTEND, "");
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

        Response originResponse = chain.proceed(request);
        return originResponse;
    }
}
