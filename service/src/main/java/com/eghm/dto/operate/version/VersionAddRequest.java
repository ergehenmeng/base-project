package com.eghm.dto.operate.version;

import com.eghm.validation.annotation.OptionString;
import com.eghm.validation.annotation.WordChecker;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * @author 二哥很猛
 * @since 2019/8/22 14:57
 */
@Data
public class VersionAddRequest {

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "上架平台 ANDROID IOS")
    @OptionString(value = {"ANDROID", "IOS"}, message = "请选择正确的上架平台")
    private String channel;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "版本号(0.0.01~99.99.99)")
    @NotBlank(message = "版本号不能为空")
    private String version;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "是否强制更新 false:否 true:是")
    @NotNull(message = "强制更新状态不能为空")
    private Boolean forceUpdate;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "下载地址,android为实际下载地址,ios是跳转到app_store")
    @NotBlank(message = "下载地址不能为空")
    private String url;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "备注信息:版本更新的东西或解决的问题")
    @WordChecker(message = "备注信息存在敏感词")
    private String remark;
}
