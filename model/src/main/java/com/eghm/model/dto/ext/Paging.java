package com.eghm.model.dto.ext;

import com.github.pagehelper.PageInfo;
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
public class Paging<T> {

    private static final long serialVersionUID = 9015209122071749218L;
    /**
     * 总条数
     */
    @ApiModelProperty("总条数")
    private Integer total;

    /**
     * 数据集
     */
    @ApiModelProperty("结果集")
    private List<T> rows;

    /**
     * 当前页数
     */
    @ApiModelProperty("第几页")
    private Integer page;

    /**
     * 页容量
     */
    @ApiModelProperty("页容量")
    private Integer pageSize;

    public Paging(Integer total,List<T> rows){
        this.total = total;
        this.rows = rows;
    }

    /**
     * 后台列表使用
     * @param info pageHelper对象
     */
    public Paging(PageInfo<T> info){
        this.total = (int)info.getTotal();
        this.rows = info.getList();
        this.page = info.getPageNum();
        this.pageSize = info.getPageSize();
    }

    public Paging(){
    }

    /**
     * 不分页
     * @param rows 所有列表
     */
    public Paging(List<T> rows){
        this.rows = rows;
    }

}
