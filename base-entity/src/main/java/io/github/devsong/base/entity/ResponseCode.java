package io.github.devsong.base.entity;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;

/**
 * 通用的异常返回Code
 *
 * @author guanzhisong
 * @date 2021-07-15
 */
@Getter
public enum ResponseCode {
    SYS_ERROR(-1, "系统异常"),

    SUCCESS(0, "success"),

    BIZ_ERROR(1, "业务异常");

    private static final Map<Integer, ResponseCode> HOLDER = new HashMap<Integer, ResponseCode>();

    static {
        for (ResponseCode e : values()) {
            HOLDER.put(e.code, e);
        }
    }

    int code;

    String msg;

    ResponseCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static ResponseCode get(int code) {
        return HOLDER.get(code);
    }

    public static String getMsg(int code) {
        ResponseCode e = HOLDER.get(code);
        return e == null ? "" : e.msg;
    }
}
