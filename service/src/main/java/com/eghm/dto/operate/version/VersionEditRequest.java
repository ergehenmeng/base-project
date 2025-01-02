package com.eghm.dto.operate.version;

import com.eghm.validation.annotation.WordChecker;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotNull;

/**
 * @author 二哥很猛
 * @since 2019/8/22 19:32
 */
@Data
public class VersionEditRequest {

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "id")
    @NotNull(message = "id不能为空")
    private Long id;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "是否强制更新 false:否 true:是")
    @NotNull(message = "强制更新状态不能为空")
    private Boolean forceUpdate;

    @Schema(description = "备注信息:版本更新的东西或解决的问题")
    @WordChecker(message = "备注信息存在敏感词")
    private String remark;
}
