package io.github.devsong.base.common.aspect;


import com.google.common.base.Stopwatch;
import io.github.devsong.base.common.HttpStatus;
import io.github.devsong.base.common.util.IpUtil;
import io.github.devsong.base.common.util.JsonUtil;
import io.github.devsong.base.entity.SysPerfLogDto;
import io.github.devsong.base.entity.annotation.PerfLog;
import io.github.devsong.base.log.trace.TraceContext;
import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.beans.BeanMap;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 记录接口请求中的参数,类似于nginx/httpd中的access log功能
 *
 * @author guanzhisong
 */
@Slf4j
public abstract class PerfBaseAspect extends SysAbstractAspect {
    protected Object perfLog(ProceedingJoinPoint point) throws Throwable {
        // 访问目标方法的参数：
        MethodSignature sig = (MethodSignature) point.getSignature();
        Object target = point.getTarget();
        Method currentMethod = target.getClass().getMethod(sig.getName(), sig.getParameterTypes());
        String clazz = sig.getDeclaringTypeName();
        String methodName = currentMethod.getName();
        PerfLog perfLog = getPerfLogAnnotation(point);
        boolean recordFlag = true;
        if (perfLog != null) {
            clazz = StringUtils.isNotBlank(perfLog.clazz()) ? perfLog.clazz() : clazz;
            methodName = StringUtils.isNotBlank(perfLog.method()) ? perfLog.method() : methodName;
            if (perfLog.ignore()) {
                recordFlag = false;
            }
        }
        Object[] args = point.getArgs();
        Object returnValue = null;
        Exception exception = null;
        long elapsed;
        Stopwatch stopwatch = Stopwatch.createStarted();
        try {
            returnValue = point.proceed(args);
        } catch (Exception e) {
            // 记录系统调用异常日志
            exception = e;
            throw e;
        } finally {
            // 记录方法调用日志
            stopwatch.stop();
            elapsed = stopwatch.elapsed(TimeUnit.MILLISECONDS);
            if (recordFlag) {
                // 记录日志
                try {
                    recordPerfLog(clazz, methodName, returnValue, exception, elapsed);
                } catch (Exception e) {
                    log.error("record perflog error", e);
                }
            }
        }
        return returnValue;
    }

    /**
     * 记录性能日志
     */
    @SuppressWarnings("unchecked")
    protected void recordPerfLog(String clazz, String methodName, Object returnValue, Exception exception, long elapsed) {
        int code = 0;
        String errorMsg = "success";
        if (exception != null) {
            // 异常非空,系统异常
            code = -1;
            errorMsg = JsonUtil.toJSONString(exception);
        } else {
            if (returnValue == null) {
                // 返回值位空,业务异常
                code = 1;
                errorMsg = "null";
            } else {
                Map<String, Object> m;
                if (returnValue instanceof Map) {
                    m = (Map<String, Object>) returnValue;
                } else {
                    m = (Map<String, Object>) BeanMap.create(returnValue);
                }
                if (m.containsKey(FIELD_CODE)) {
                    code = Integer.parseInt(String.valueOf(m.get(FIELD_CODE).toString()));
                    if (Objects.equals(code, HttpStatus.SUCCESS)) {
                        // code转换
                        code = 0;
                    }
                }
                if (m.containsKey(FIELD_MSG)) {
                    errorMsg = String.valueOf(m.get(FIELD_MSG));
                }
            }
        }

        errorMsg = StringUtils.left(errorMsg, MAX_STR_LEN);

        SysPerfLogDto sysPerfLogDto = SysPerfLogDto.builder()
                .traceId(TraceContext.get().getTraceId())
                .spanId(TraceContext.get().getSpanId())
                .product(appCoordinate().getProduct())
                .groupName(appCoordinate().getGroup())
                .app(appCoordinate().getApp())
                .clazz(clazz)
                .method(methodName)
                .code(code)
                .errmsg(errorMsg)
                .instance(IpUtil.getLocalIp())
                .executeTimespan((int) elapsed)
                .createTime(LocalDateTime.now())
                .build();
        // 记录性能
        PERF_LOG.info(JsonUtil.toJSONString(sysPerfLogDto));
    }

    protected PerfLog getPerfLogAnnotation(ProceedingJoinPoint point) {
        MethodSignature signature = (MethodSignature) point.getSignature();
        PerfLog perfLog = AnnotationUtils.findAnnotation(signature.getMethod(), PerfLog.class);
        if (Objects.nonNull(perfLog)) {
            return perfLog;
        }

        return AnnotationUtils.findAnnotation(signature.getDeclaringType(), PerfLog.class);
    }

}
