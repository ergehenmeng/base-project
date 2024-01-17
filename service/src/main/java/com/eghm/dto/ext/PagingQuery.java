package com.eghm.dto.ext;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.constant.CommonConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 基础类请求参数 如需分页操作需要继承该方法
 *
 * @author 二哥很猛
 * @date 2018/1/12 17:29
 */
@Getter
@Setter
public class PagingQuery {

    @ApiModelProperty(value = "第几页", required = true, example = "1")
    private Integer page = 1;

    @ApiModelProperty(value = "页容量", required = true, example = "10")
    private Integer pageSize = 10;

    @ApiModelProperty(hidden = true)
    private String queryName;

    /**
     * 创建分页对象
     *
     * @param <T> 查询对象
     * @return 分页对象
     */
    public <T> Page<T> createPage() {
        return new Page<>(this.getPage(), this.getPageSize());
    }

    /**
     * 创建分页对象
     *
     * @param count 是否统计总条数
     * @param <T>   查询对象
     * @return 分页对象
     */
    public <T> Page<T> createPage(boolean count) {
        return new Page<>(this.getPage(), this.getPageSize(), count);
    }

    /**
     * 防止前端页面传递分页参数过大
     *
     * @return 最大 20
     */
    public Integer getPageSize() {
        return pageSize > CommonConstant.MAX_PAGE_SIZE ? CommonConstant.MAX_PAGE_SIZE : pageSize;
    }

    /**
     * 默认第一页
     *
     * @return 1
     */
    public Integer getPage() {
        return page <= 0 ? 1 : page;
    }

    /**
     * 创建分页对象 用于导出excel
     *
     * @param <T>   查询对象
     * @return 分页对象
     */
    public <T> Page<T> createNullPage() {
        return new Page<>(this.getPage(), -1, false);
    }
}
