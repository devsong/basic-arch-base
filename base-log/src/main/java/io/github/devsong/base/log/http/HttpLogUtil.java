package io.github.devsong.base.log.http;

import io.github.devsong.base.entity.SysPerfHttpLogDto;
import io.github.devsong.base.log.JacksonUtil;
import io.github.devsong.base.log.trace.TraceContext;
import io.github.devsong.base.log.trace.Tracer;
import java.time.LocalDateTime;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * date:  2024/6/15
 * author:guanzhisong
 */
@Slf4j
public class HttpLogUtil {
    protected static final Logger HTTP_PERF_LOGGER = LoggerFactory.getLogger("sysHttpPerfLogger");

    @SneakyThrows
    public static void recordHttpPerfLog(String host, String path, String httpMethod, int respCode, long elapsed) {
        try {
            Tracer tracer = TraceContext.get();
            SysPerfHttpLogDto sysPerfLogDto = SysPerfHttpLogDto.builder()
                    .traceId(tracer == null ? "" : tracer.getTraceId())
                    .spanId(tracer == null ? "" : tracer.getSpanId())
                    .host(host)
                    .url(path)
                    .method(httpMethod)
                    .code(respCode)
                    .instance(IpUtil.getLocalIp())
                    .executeTimespan((int) elapsed)
                    .createTime(LocalDateTime.now())
                    .build();
            // 记录性能日志
            HTTP_PERF_LOGGER.info(JacksonUtil.mapper().writeValueAsString(sysPerfLogDto));
        } catch (Exception e) {
            log.error("record http perf log error", e);
        }
    }
}
