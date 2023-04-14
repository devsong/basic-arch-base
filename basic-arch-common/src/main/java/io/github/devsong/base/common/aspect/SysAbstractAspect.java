package io.github.devsong.base.common.aspect;


import io.github.devsong.base.common.AppCoordinate;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 记录接口请求中的参数,类似于nginx/httpd中的access log功能
 *
 * @author guanzhisong
 */
@Slf4j
public abstract class SysAbstractAspect {
    protected static final Logger PERF_LOG = LoggerFactory.getLogger("sysPerfLogger");
    protected static final Logger PARAM_LOG = LoggerFactory.getLogger("sysParamLogger");
    protected static final String FIELD_CODE = "code";
    protected static final String FIELD_MSG = "msg";
    protected static final int MAX_STR_LEN = 2 << 12;

    protected static final String REST_POINT_CUT = "@within(org.springframework.web.bind.annotation.RestController)";
    protected static final String ANNOTATION_PERFLOG = "@within(io.github.devsong.base.entity.annotation.PerfLog)";

    /**
     * 获取应用的标识
     *
     * @return
     */
    public abstract AppCoordinate appCoordinate();
}
