package com.fanyin.dto.system.config;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 二哥很猛
 * @date 2018/1/29 16:07
 */
@Data
public class ConfigAddRequest implements Serializable{

    private static final long serialVersionUID = -5215129909970715975L;

    /**
     * 系统参数标示符
     */
    private String nid;

    /**
     * 参数名
     */
    private String title;

    /**
     * 系统参数值
     */
    private String content;

    /**
     * 参数类型
     */
    private Byte classify;

    /**
     * 是否锁定 锁定即不可编辑
     */
    private Boolean locked;

    /**
     * 备注信息
     */
    private String remark;

    /**
     * 有效开始时间
     */
    private Date startTime;

    /**
     * 有效结束时间
     */
    private Date endTime;

    /**
     * 备选值
     */
    private String reserveContent;
}
