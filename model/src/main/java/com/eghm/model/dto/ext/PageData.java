package com.eghm.model.dto.ext;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 分页返回前台的对象
 * @author 二哥很猛
 * @date 2018/1/18 15:35
 */
@Data
@ApiModel("分页响应基础参数")
public class PageData<T> {

    private static final long serialVersionUID = 9015209122071749218L;
    /**
     * 总条数
     */
    @ApiModelProperty("总条数")
    private Long total;

    /**
     * 数据集
     */
    @ApiModelProperty("结果集")
    private List<T> rows;

    /**
     * 当前页数
     */
    @ApiModelProperty("第几页")
    private Long page;

    /**
     * 页容量
     */
    @ApiModelProperty("页容量")
    private Long pageSize;

    /**
     * 减少不必要的参数给前端
     * @param info 分页信息
     * @param <T> T
     * @return data
     */
    public static <T> PageData<T> toPage(Page<T> info) {
        PageData<T> pageData = new PageData<>();
        pageData.total = info.getTotal();
        pageData.rows = info.getRecords();
        pageData.page = info.getCurrent();
        pageData.pageSize = info.getSize();
        return pageData;
    }


    /**
     * 减少不必要的参数给前端(不分页形式)
     * @param list 分页信息
     * @param <T> T
     * @return data
     */
    public static <T> PageData<T> toList(List<T> list) {
        PageData<T> pageData = new PageData<>();
        pageData.total = (long) list.size();
        pageData.rows = list;
        pageData.page = 1L;
        pageData.pageSize = (long) list.size();
        return pageData;
    }

}
