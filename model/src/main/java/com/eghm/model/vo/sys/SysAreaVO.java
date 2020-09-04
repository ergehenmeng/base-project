package com.eghm.model.vo.sys;

import lombok.Data;

/**
 * @author 殿小二
 * @date 2020/9/3
 */
@Data
public class SysAreaVO {

    /**
     * nid
     */
    private Long nid;

    /**
     * 地区名称
     */
    private String title;

    /**
     * 邮编
     */
    private String zipCode;

    /**
     * 首字母
     */
    private String mark;

}
