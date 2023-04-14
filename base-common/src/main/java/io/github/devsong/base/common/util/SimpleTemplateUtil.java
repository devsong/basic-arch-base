package io.github.devsong.base.common.util;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 简易的字符模版工具类
 * @author guanzhisong
 */
public class SimpleTemplateUtil {
    private static final Pattern P = Pattern.compile("\\$\\{\\w+\\}");

    public static String processTemplate(String template, Map<String, String> params) {
        StringBuffer sb = new StringBuffer();
        Matcher m = P.matcher(template);
        while (m.find()) {
            String param = m.group();
            Object value = params.get(param.substring(2, param.length() - 1));
            m.appendReplacement(sb, value == null ? "" : value.toString());
        }
        m.appendTail(sb);
        return sb.toString();
    }
}
