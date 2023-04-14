package io.github.devsong.base.entity;

/**
 * 默认时间查询范围工具类
 * @author guanzhisong
 *
 */
public class DefaultParamUtil {

    /**
     * 设置默认分页参数
     *
     * @param pageRequestDto
     */
    public static void setDefaultSearchRange(PageRequestDto pageRequestDto) {
        if (pageRequestDto.getPageNum() == null) {
            pageRequestDto.setPageNum(1);
        }
        if (pageRequestDto.getPageSize() == null) {
            pageRequestDto.setPageSize(10);
        }
    }
}
