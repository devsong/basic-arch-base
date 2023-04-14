package io.github.devsong.base.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResponseDto<T> implements Serializable {
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
    protected List<T> data;
    /**
     * 分页数据对象
     */
    protected PageResponse page;


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

    public boolean isSuccess() {
        return this != null && this.getCode() == ResponseCode.SUCCESS.getCode();
    }

    public static <T> PageResponseDto<T> build(int code, String msg, List<T> data, PageResponse page) {
        PageResponseDto<T> pageResponseDto = new PageResponseDto<>();
        pageResponseDto.setCode(code);
        pageResponseDto.setMsg(msg);
        pageResponseDto.setData(data);
        pageResponseDto.setPage(page);
        return pageResponseDto;
    }

    /**
     * 成功的结果集
     *
     * @param data
     * @param page
     * @param pageSize
     * @return
     */
    public static <T> PageResponseDto<T> success(List<T> data, int page, int pageSize) {
        return success(data, page, pageSize, 0);
    }

    /**
     * 成功的结果集
     *
     * @param data
     * @param page
     * @param pageSize
     * @param total
     * @return
     */
    public static <T> PageResponseDto<T> success(List<T> data, int page, int pageSize, int total) {
        PageResponse pageResponse = PageResponse.builder().page(page).pageSize(pageSize).total(total).build();
        return success(data, pageResponse);
    }

    /**
     * 成功的结果集
     *
     * @param data
     * @param page
     * @return
     */
    public static <T> PageResponseDto<T> success(List<T> data, PageResponse page) {
        return build(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMsg(), data, page);
    }

    /**
     * 业务异常结果
     *
     * @param errorCode
     * @param errorMsg
     * @return
     */
    public static <T> PageResponseDto<T> bizError(String errorMsg) {
        return error(ResponseCode.BIZ_ERROR.getCode(), errorMsg);
    }

    /**
     * 系统异常结果
     *
     * @param errorCode
     * @param errorMsg
     * @return
     */
    public static <T> PageResponseDto<T> sysError(String errorMsg) {
        return error(ResponseCode.SYS_ERROR.getCode(), errorMsg);
    }

    /**
     * 异常结果
     *
     * @param errorCode
     * @param errorMsg
     * @return
     */
    public static <T> PageResponseDto<T> error(int errorCode, String errorMsg) {
        PageResponse pageResponse = PageResponse.builder().page(1).pageSize(1).build();
        return build(errorCode, errorMsg, null, pageResponse);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class PageResponse implements Serializable {
        /**
         *
         */
        private static final long serialVersionUID = 1L;
        // 空的page对象
        private static final PageResponse NULL_PAGE = new PageResponse(0, 1, 0);
        protected Integer page;
        protected Integer pageSize;
        protected Integer total;

        public Integer getTotalPage() {
            return total % pageSize == 0 ? total / pageSize : total / pageSize + 1;
        }

        public static PageResponse getNullPage() {
            return NULL_PAGE;
        }
    }
}
