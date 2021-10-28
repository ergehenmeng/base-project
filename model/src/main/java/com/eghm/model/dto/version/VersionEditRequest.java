package com.eghm.model.dto.version;

import com.eghm.model.validation.annotation.OptionString;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author 二哥很猛
 * @date 2019/8/22 19:32
 */
@Data
public class VersionEditRequest implements Serializable {

    private static final long serialVersionUID = 8670667666853071583L;

    @ApiModelProperty(required = true, value = "id")
    @NotNull(message = "id不能为空")
    private Long id;

    @ApiModelProperty(required = true, value = "上架平台 ANDROID, IOS")
    @OptionString({"ANDROID", "IOS"})
    private String classify;
    
    @ApiModelProperty(required = true, value = "上架状态 0:待上架 1:已上架")
    @NotNull(message = "上架状态不能为空")
    private Byte state;
    
    @ApiModelProperty(required = true, value = "版本号(0.0.01~99.99.99)")
    @NotNull(message = "版本号不能为空")
    private String version;

    @ApiModelProperty(required = true, value = "是否强制更新 false:否 true:是")
    @NotNull(message = "强制更新状态不能为空")
    private Boolean forceUpdate;

    @ApiModelProperty(required = true, value = "下载地址,android为实际下载地址,ios是跳转到app_store")
    @NotNull(message = "下载地址不能为空")
    private String url;

    @ApiModelProperty(required = true, value = "备注信息:版本更新的东西或解决的问题")
    private String remark;
}
