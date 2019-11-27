package com.fanyin.dao.model.system;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 二哥很猛
 */
@Data
public class TagView implements Serializable {

    /**
     * <br>
     * 表 : tag_view<br>
     * 对应字段 : id<br>
     */
    private Integer id;

    /**
     * 页面名称<br>
     * 表 : tag_view<br>
     * 对应字段 : title<br>
     */
    private String title;

    /**
     * view唯一标示符<br>
     * 表 : tag_view<br>
     * 对应字段 : tag<br>
     */
    private String tag;

    /**
     * view页面涉及到的接口,分号分割<br>
     * 表 : tag_view<br>
     * 对应字段 : url<br>
     */
    private String url;

    /**
     * 备注信息<br>
     * 表 : tag_view<br>
     * 对应字段 : remark<br>
     */
    private String remark;

}