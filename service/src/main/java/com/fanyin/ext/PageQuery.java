package com.fanyin.ext;

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
public class PageQuery implements Serializable{

    private static final long serialVersionUID = 9015209122071749218L;

    /**
     * 第几页
     */
    private int page = 1;

    /**
     * 页容量
     */
    private int pageSize = 20;

    /**
     * 基础查询字段
     */
    private String queryName;


}
