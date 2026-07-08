package com.eghm.dto.operate.notice;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 二哥很猛
 * @since 2018/11/20 19:17
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class NoticeEditRequest extends NoticeAddRequest {

    @Schema(description = "id主键", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "id不能为空")
    private Long id;

}
