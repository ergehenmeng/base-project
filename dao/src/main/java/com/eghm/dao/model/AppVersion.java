package com.eghm.dao.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


import java.util.Date;

/**
 * 手机版本
 * @author 二哥很猛
 */
@Getter
@Setter
@ToString(callSuper = true)
public class AppVersion extends BaseEntity {

    /**
     * 版本类型 IOS,ANDROID<br>
     * 表 : app_version<br>
     * 对应字段 : classify<br>
     */
    private String classify;

    /**
     * 版本号:1.2.8<br>
     * 表 : app_version<br>
     * 对应字段 : version<br>
     */
    private String version;

    /**
     * 版本号:10208<br>
     * 表 : app_version<br>
     * 对应字段 : version_no<br>
     */
    private Integer versionNo;

    /**
     * 该版本是否强制更新版本 0:否 1:是<br>
     * 表 : app_version<br>
     * 对应字段 : force_update<br>
     */
    private Boolean forceUpdate;

    /**
     * 下载地址,android为实际下载地址,ios是跳转到app_store<br>
     * 表 : app_version<br>
     * 对应字段 : url<br>
     */
    private String url;

    /**
     * 上传时间<br>
     * 表 : app_version<br>
     * 对应字段 : add_time<br>
     */
    private Date addTime;

    /**
     * 备注信息:版本更新的东西或解决的问题<br>
     * 表 : app_version<br>
     * 对应字段 : remark<br>
     */
    private String remark;

    /**
     * 删除状态 0:未删除 1:已删除<br>
     * 表 : app_version<br>
     * 对应字段 : deleted<br>
     */
    private Boolean deleted;

    /**
     * 更新时间<br>
     * 表 : app_version<br>
     * 对应字段 : update_time<br>
     */
    private Date updateTime;

    /**
     * 上架状态 0:待上架 1:已上架<br>
     * 表 : app_version<br>
     * 对应字段 : state<br>
     */
    private Byte state;

    private static final long serialVersionUID = 1L;

}