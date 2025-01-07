package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 手机版本
 *
 * @author 二哥很猛
 */
@Data
@TableName("app_version")
@EqualsAndHashCode(callSuper = true)
public class AppVersion extends BaseEntity {

    @Schema(description = "版本类型 IOS,ANDROID")
    private String channel;

    @Schema(description = "版本号:1.2.8 最大:xx.xx.xx")
    private String version;

    @Schema(description = "版本号:10208")
    private Integer versionNo;

    @Schema(description = "上架状态 false:待上架 true:已上架")
    private Boolean state;

    @Schema(description = "该版本是否强制更新版本 0:否 1:是")
    private Boolean forceUpdate;

    @Schema(description = "下载地址,android为实际下载地址,ios是跳转到app_store")
    private String url;

    @Schema(description = "备注信息:版本更新的东西或解决的问题")
    private String remark;

}