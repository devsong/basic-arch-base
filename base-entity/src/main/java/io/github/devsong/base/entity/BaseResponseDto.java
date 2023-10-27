package io.github.devsong.base.entity;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponseDto<T> implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * 返回值
     */
    protected int code;
    /**
     * 返回描述信息
     */
    protected String msg;
    /**
     * 返回对象实体
     */
    protected T data;

    ///////////////////////////////////////////////////////
    // 以下两个参数为辅助参数,用于返回接口耗时以及接口执行机器IP
    ///////////////////////////////////////////////////////
    /**
     * 接口耗时
     */
    protected long elapsed;

    /**
     * 后端服务器
     */
    protected String server;

    /**
     * 响应结果是否成功
     *
     * @return
     */
    public static boolean isSuccess(BaseResponseDto<?> responseDto) {
        return responseDto != null && responseDto.getCode() == ResponseCode.SUCCESS.getCode();
    }

    /**
     * 基础的构造方法
     *
     * @param code
     * @param msg
     * @param data
     * @return
     */
    public static <T> BaseResponseDto<T> build(int code, String msg, T data) {
        BaseResponseDto<T> baseResponseDto = new BaseResponseDto<>();
        baseResponseDto.setCode(code);
        baseResponseDto.setMsg(msg);
        baseResponseDto.setData(data);
        return baseResponseDto;
    }

    /**
     * 成功的结果集
     *
     * @param data
     * @return
     */
    public static <T> BaseResponseDto<T> success(T data) {
        return build(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMsg(), data);
    }

    /**
     * 业务异常结果
     *
     * @param errorMsg
     * @return
     */
    public static <T> BaseResponseDto<T> bizError(String errorMsg) {
        return error(ResponseCode.BIZ_ERROR.getCode(), errorMsg);
    }

    /**
     * 系统异常结果
     *
     * @param errorMsg
     * @return
     */
    public static <T> BaseResponseDto<T> sysError(String errorMsg) {
        return error(ResponseCode.SYS_ERROR.getCode(), errorMsg);
    }

    /**
     * 异常结果
     *
     * @param errorCode
     * @param errorMsg
     * @return
     */
    public static <T> BaseResponseDto<T> error(int errorCode, String errorMsg) {
        return build(errorCode, errorMsg, null);
    }
}
