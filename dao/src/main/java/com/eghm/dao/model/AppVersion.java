package com.eghm.dao.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 手机版本
 * @author 二哥很猛
 */
@Getter
@Setter
@ToString(callSuper = true)
public class AppVersion extends BaseEntity {

    @ApiModelProperty("版本类型 IOS,ANDROID")
    private String classify;

    @ApiModelProperty("版本号:1.2.8 最大:xx.xx.xx")
    private String version;

    @ApiModelProperty("版本号:10208")
    private Integer versionNo;

    @ApiModelProperty("该版本是否强制更新版本 0:否 1:是")
    private Boolean forceUpdate;

    @ApiModelProperty("下载地址,android为实际下载地址,ios是跳转到app_store")
    private String url;

    @ApiModelProperty("备注信息:版本更新的东西或解决的问题")
    private String remark;

    @ApiModelProperty("删除状态 0:未删除 1:已删除")
    private Boolean deleted;

    @ApiModelProperty("上架状态 0:待上架 1:已上架")
    private Byte state;

}