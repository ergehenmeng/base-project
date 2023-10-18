package com.eghm.dto.version;

import com.eghm.validation.annotation.OptionString;
import com.eghm.validation.annotation.WordChecker;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author 二哥很猛
 * @date 2019/8/22 14:57
 */
@Data
public class VersionAddRequest implements Serializable {

    private static final long serialVersionUID = 4277066521664563340L;
    
    @ApiModelProperty(required = true, value = "上架平台 ANDROID, IOS")
    @OptionString(value = {"ANDROID", "IOS"}, message = "上架平台参数非法")
    private String classify;
    
    @ApiModelProperty(required = true, value = "上架状态 0:待上架 1:已上架")
    @NotNull(message = "上架状态不能为空")
    private Integer state;
    
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
    @WordChecker
    private String remark;
}
