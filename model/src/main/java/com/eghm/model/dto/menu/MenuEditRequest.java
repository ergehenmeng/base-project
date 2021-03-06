package com.eghm.model.dto.menu;

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
    private Long id;

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
    private Long pid;

    /**
     * 菜单url
     */
    private String url;

    /**
     * 子url
     */
    private String subUrl;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 备注
     */
    private String remark;

}
