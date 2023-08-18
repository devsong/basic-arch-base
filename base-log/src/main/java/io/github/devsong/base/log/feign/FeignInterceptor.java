package io.github.devsong.base.log.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;

import java.util.Map;
import java.util.Optional;

import static com.google.common.collect.Maps.newHashMap;
import static io.github.devsong.base.log.trace.TraceConstants.*;

/**
 * @author zhisong.guan
 * @date 2022/10/15 12:50
 */
@Slf4j
public class FeignInterceptor implements RequestInterceptor {
    @Value("${spring.application.name}")
    private String appName;

    public void apply(RequestTemplate template) {
        try {
            Map<String, String> map =
                    Optional.ofNullable(MDC.getCopyOfContextMap()).orElse(newHashMap());
            template.header(FeignHeaderConstant.PROJECT, appName);
            template.header(TRACE_ID, map.getOrDefault(TRACE_ID, ""));
            template.header(SPAN_ID, map.getOrDefault(SPAN_ID, ""));
            template.header(TRACE_EXTEND, map.getOrDefault(TRACE_EXTEND, ""));
        } catch (Exception e) {
            log.warn("error setting request header, " + e.getMessage(), e);
        }
    }
}
