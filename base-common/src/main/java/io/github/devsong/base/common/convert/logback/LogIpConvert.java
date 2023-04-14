package io.github.devsong.base.common.convert.logback;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import io.github.devsong.base.common.util.IpUtil;

/**
 * @author zhisong.guan
 * @date 2022/10/9 14:19
 */
public class LogIpConvert extends ClassicConverter {
    private static final String IP = IpUtil.getLocalIp("en0");

    @Override
    public String convert(ILoggingEvent event) {
        return IP;
    }
}
