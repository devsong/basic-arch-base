package io.github.devsong.base.entity;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class PageRequestDto implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * 页号
     */
    protected Integer pageNum;
    /**
     * 页大小
     */
    protected Integer pageSize;
    /**
     * 兼容已有分页逻辑
     */
    protected Integer limit;

    public Integer getPageSize() {
        return pageSize == null ? limit : pageSize;
    }

    public Integer getOffset() {
        return pageNum <= 1 ? 0 : (pageNum - 1) * getPageSize();
    }
}
