package com.fanyin.dto.system.menu;

import lombok.Data;

import java.io.Serializable;

/**
 * 菜单添加
 * @author 二哥很猛
 * @date  2018/1/30 11:36
 */
@Data
public class MenuAddRequest implements Serializable {

    private static final long serialVersionUID = 8515421119279590820L;

    /**
     * 名称
     */
    private String title;

    /**
     * 标示符
     */
    private String nid;

    /**
     * 菜单url
     */
    private String url;

    /**
     * 子url
     */
    private String subUrl;

    /**
     * 父id
     */
    private Integer pid;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 备注信息
     */
    private String remark;

    /**
     * 是否为主菜单
     */
    private Byte classify;

}
