package com.fanyin.dto.system.menu;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 二哥很猛
 */
@Data
public class MenuEditRequest implements Serializable {

    private static final long serialVersionUID = 6714241304584747778L;

    /**
     * 主键
     */
    private Integer id;

    /**
     * 菜单名称
     */
    private String title;

    /**
     * 菜单标示符
     */
    private String nid;

    /**
     * 菜单父id
     */
    private Integer pid;

    /**
     * 菜单url
     */
    private String url;

    /**
     * 子url
     */
    private String subUrl;

    /**
     * 是否为左侧主菜单
     */
    private Byte classify;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 备注
     */
    private String remark;

}
