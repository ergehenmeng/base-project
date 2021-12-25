package com.eghm.model.dto.ext;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 基础类请求参数 如需分页操作需要继承该方法
 * @author 二哥很猛
 * @date 2018/1/12 17:29
 */
@Getter
@Setter
@ApiModel("分页请求基础参数")
public class PagingQuery implements Serializable{

    private static final long serialVersionUID = 9015209122071749218L;

    /**
     * 第几页
     */
    @ApiModelProperty(value = "第几页",required = true)
    private Integer page = 1;

    /**
     * 页容量
     */
    @ApiModelProperty(value = "页容量",required = true)
    private Integer pageSize = 20;

    /**
     * 基础查询字段
     */
    @ApiModelProperty(hidden = true)
    private String queryName;


    /**
     * 创建分页对象
     * @param <T> 查询对象
     * @return 分页对象
     */
    public <T> Page<T> createPage() {
        return new Page<>(page, pageSize);
    }

}
