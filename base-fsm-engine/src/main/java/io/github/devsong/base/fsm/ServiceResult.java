package io.github.devsong.base.fsm;

import lombok.Builder;
import lombok.Data;

/**
 * date:  2024/6/15
 * author:guanzhisong
 */
@Data
@Builder
public class ServiceResult<T> {
    public static final int SUCCESS = 0;
    public static final int FAIL = 1;
    private int code;
    private String msg;
    private T result;

    public ServiceResult() {
        this.code = SUCCESS;
    }

    public ServiceResult(int code, String msg, T result) {
        this.code = code;
        this.msg = msg;
        this.result = result;
    }

    public static <T> ServiceResult<T> buildSuccess(T data) {
        return new ServiceResult<>(SUCCESS, "success", data);
    }

    public static <T> ServiceResult<T> buildFail(T data) {
        return new ServiceResult<>(FAIL, "fail", data);
    }

    public boolean isSuccess() {
        return this.code == SUCCESS;
    }
}
