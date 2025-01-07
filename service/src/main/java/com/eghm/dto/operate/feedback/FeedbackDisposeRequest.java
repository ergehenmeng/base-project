package com.eghm.dto.operate.feedback;

import com.eghm.dto.ext.ActionRecord;
import com.eghm.validation.annotation.WordChecker;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * 反馈处理
 *
 * @author 二哥很猛
 * @since 2019/8/28 14:26
 */
@Setter
@Getter
public class FeedbackDisposeRequest extends ActionRecord {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "id不能为空")
    private Long id;

    @Schema(description = "回复信息", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "回复信息不能为空")
    @WordChecker(message = "回复信息存在敏感字")
    private String remark;

}
