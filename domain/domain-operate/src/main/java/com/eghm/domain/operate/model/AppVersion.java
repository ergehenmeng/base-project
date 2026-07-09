package com.eghm.domain.operate.model;

import com.eghm.domain.shared.model.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 手机版本
 *
 * @author 二哥很猛
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AppVersion extends BaseEntity {

    /** 版本类型 IOS,ANDROID */
    private String channel;

    /** 版本号:1.2.8 最大:xx.xx.xx */
    private String version;

    /** 版本号:10208 */
    private Integer versionNo;

    /** 上架状态 false:待上架 true:已上架 */
    private Boolean state;

    /** 该版本是否强制更新版本 0:否 1:是 */
    private Boolean forceUpdate;

    /** 下载地址,android为实际下载地址,ios是跳转到app_store */
    private String url;

    /** 备注信息:版本更新的东西或解决的问题 */
    private String remark;

    /**
     * 初始化待上架版本.
     *
     * @param versionNo 数字版本号
     */
    public void initialize(Integer versionNo) {
        this.versionNo = versionNo;
        this.state = false;
    }

    /**
     * 修改上架状态.
     *
     * @param state 上架状态
     */
    public void changeState(Boolean state) {
        this.state = state;
    }
}
