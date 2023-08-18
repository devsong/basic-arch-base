package io.github.devsong.base.common.util;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

public class ServletUtil {

    public static HttpServletRequest getRequest() {
        RequestAttributes attrs = RequestContextHolder.getRequestAttributes();
        if (attrs == null) {
            return null;
        }
        HttpServletRequest request = ((ServletRequestAttributes) attrs).getRequest();
        return request;
    }

    public static HttpServletResponse getResponse() {
        RequestAttributes attrs = RequestContextHolder.getRequestAttributes();
        if (attrs == null) {
            return null;
        }
        HttpServletResponse response = ((ServletRequestAttributes) attrs).getResponse();
        return response;
    }

    public static String getRequestBodyAsString() throws IOException {
        HttpServletRequest request = getRequest();
        if (request == null) {
            return null;
        }
        BufferedReader br = request.getReader();
        StringBuilder sb = new StringBuilder();
        String str;
        while ((str = br.readLine()) != null) {
            sb.append(str);
        }
        return sb.toString();
    }

    public static String getClientIp() {
        return IpUtil.getIpAdrress(getRequest());
    }
}
