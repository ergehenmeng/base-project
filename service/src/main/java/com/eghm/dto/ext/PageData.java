package com.eghm.dto.ext;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 分页返回前台的对象
 *
 * @author 二哥很猛
 * @since 2018/1/18 15:35
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PageData<T> {

    @ApiModelProperty("总条数")
    private Integer total;

    @ApiModelProperty("结果集")
    private List<T> rows;

    @ApiModelProperty("第几页")
    private Integer page;

    @ApiModelProperty("页容量")
    private Integer pageSize;

    /**
     * 减少不必要的参数给前端
     *
     * @param info 分页信息
     * @param <T>  T
     * @return data
     */
    public static <T> PageData<T> toPage(Page<T> info) {
        PageData<T> pageData = new PageData<>();
        pageData.total = (int) info.getTotal();
        pageData.rows = info.getRecords();
        pageData.page = (int) info.getCurrent();
        pageData.pageSize = (int) info.getSize();
        return pageData;
    }

}
