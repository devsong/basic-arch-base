package io.github.devsong.base.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ApiModel("分页对象")
public class PageRequestDto implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * 页号
     */
    @ApiModelProperty(value = "页号", example = "1", required = true)
    protected Integer pageNum;
    /**
     * 页大小
     */
    @ApiModelProperty(value = "分页大小", example = "10", required = true)
    protected Integer pageSize;
    /**
     * 兼容已有分页逻辑
     */
    @JsonIgnore()
    protected Integer limit;

    public Integer getPageSize() {
        return pageSize == null ? limit : pageSize;
    }

    public Integer getOffset() {
        return pageNum <= 1 ? 0 : (pageNum - 1) * getPageSize();
    }
}
