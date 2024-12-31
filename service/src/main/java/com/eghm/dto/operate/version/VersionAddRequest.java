package com.eghm.dto.operate.version;

import com.eghm.validation.annotation.OptionString;
import com.eghm.validation.annotation.WordChecker;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * @author 二哥很猛
 * @since 2019/8/22 14:57
 */
@Data
public class VersionAddRequest {

    @ApiModelProperty(required = true, value = "上架平台 ANDROID IOS")
    @OptionString(value = {"ANDROID", "IOS"}, message = "请选择正确的上架平台")
    private String channel;

    @ApiModelProperty(required = true, value = "版本号(0.0.01~99.99.99)")
    @NotBlank(message = "版本号不能为空")
    private String version;

    @ApiModelProperty(required = true, value = "是否强制更新 false:否 true:是")
    @NotNull(message = "强制更新状态不能为空")
    private Boolean forceUpdate;

    @ApiModelProperty(required = true, value = "下载地址,android为实际下载地址,ios是跳转到app_store")
    @NotBlank(message = "下载地址不能为空")
    private String url;

    @ApiModelProperty(required = true, value = "备注信息:版本更新的东西或解决的问题")
    @WordChecker(message = "备注信息存在敏感词")
    private String remark;
}
