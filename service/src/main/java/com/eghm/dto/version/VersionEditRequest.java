package com.eghm.dto.version;

import com.eghm.validation.annotation.WordChecker;
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

    @ApiModelProperty(required = true, value = "是否强制更新 false:否 true:是")
    @NotNull(message = "强制更新状态不能为空")
    private Boolean forceUpdate;

    @ApiModelProperty(value = "备注信息:版本更新的东西或解决的问题")
    @WordChecker
    private String remark;
}
