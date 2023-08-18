package io.github.devsong.base.common.aspect;

import com.google.common.base.Stopwatch;
import io.github.devsong.base.common.AjaxResult;
import io.github.devsong.base.common.util.IpUtil;
import io.github.devsong.base.common.util.JsonUtil;
import io.github.devsong.base.entity.*;
import io.github.devsong.base.log.trace.TraceContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;

import java.util.concurrent.TimeUnit;

/**
 * 记录接口请求中的参数,类似于nginx/httpd中的access log功能
 *
 * @author guanzhisong
 */
@Slf4j
public abstract class ParamBaseAspect extends SysAbstractAspect {
    protected Object paramLog(ProceedingJoinPoint point) throws Throwable {
        Object[] args = point.getArgs();

        // set default search param
        if (args.length > 1 && args[0] instanceof PageRequestDto) {
            PageRequestDto pageRequestDto = (PageRequestDto) args[0];
            DefaultParamUtil.setDefaultSearchRange(pageRequestDto);
        }

        Stopwatch stopwatch = Stopwatch.createStarted();
        Object ret = null;
        try {
            ret = point.proceed();
        } finally {
            try {
                stopwatch.stop();
                long elapsed = stopwatch.elapsed(TimeUnit.MILLISECONDS);
                // add server tick and ip
                addServerTick(elapsed, ret);
                // log param in and out
                logParamInAndOut(point, ret);
            } catch (Exception e) {
                log.error("server tick error", e);
            }
        }
        return ret;
    }

    protected void addServerTick(long elapsed, Object ret) {
        if (ret == null) {
            return;
        }
        if (ret instanceof AjaxResult) {
            ((AjaxResult) ret).put(AjaxResult.ELAPSED, elapsed);
            ((AjaxResult) ret).put(AjaxResult.SERVER, IpUtil.getLocalIp());
        }
        if (ret instanceof PageResponseDto) {
            ((PageResponseDto<?>) ret).setElapsed(elapsed);
            ((PageResponseDto<?>) ret).setServer(IpUtil.getLocalIp());
        }
        if (ret instanceof BaseResponseDto) {
            ((BaseResponseDto<?>) ret).setElapsed(elapsed);
            ((BaseResponseDto<?>) ret).setServer(IpUtil.getLocalIp());
        }
    }

    protected void logParamInAndOut(ProceedingJoinPoint point, Object returnValue) {
        Object[] args = point.getArgs();
        String argsJson = (args == null || args.length == 0) ? "" : JsonUtil.toJSONString(args);
        String retJson = (returnValue == null ? "" : JsonUtil.toJSONString(returnValue));
        argsJson = StringUtils.left(argsJson, MAX_STR_LEN);
        retJson = StringUtils.left(retJson, MAX_STR_LEN);

        SysParamLogDto sysParamLogDto = SysParamLogDto.builder()
                .traceId(TraceContext.get().getTraceId())
                .product(appCoordinate().getProduct())
                .groupName(appCoordinate().getGroup())
                .app(appCoordinate().getApp())
                .paramsIn(argsJson)
                .paramsOut(retJson)
                .build();

        // 参数日志
        PARAM_LOG.info(JsonUtil.toJSONString(sysParamLogDto));
    }
}
