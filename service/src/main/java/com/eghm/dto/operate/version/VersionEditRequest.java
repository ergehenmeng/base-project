package com.eghm.dto.operate.version;

import com.eghm.validation.annotation.WordChecker;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author 二哥很猛
 * @since 2019/8/22 19:32
 */
@Data
public class VersionEditRequest {

    @ApiModelProperty(required = true, value = "id")
    @NotNull(message = "id不能为空")
    private Long id;

    @ApiModelProperty(required = true, value = "是否强制更新 false:否 true:是")
    @NotNull(message = "强制更新状态不能为空")
    private Boolean forceUpdate;

    @ApiModelProperty(value = "备注信息:版本更新的东西或解决的问题")
    @WordChecker(message = "备注信息存在敏感词")
    private String remark;
}
