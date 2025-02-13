package com.eghm.dto.member.tag;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2024/3/6
 */

@Data
public class SendSmsRequest {

    @Schema(description = "短信模板id")
    @NotBlank(message = "请选择短信模板")
    private String templateId;

    @Schema(description = "短信模板参数,逗号分隔")
    private String params;

    @Schema(description = "会员id(二选一优先级最高)")
    private List<Long> memberIds;

    @Schema(description = "标签id(二选一优先级次之)")
    private Long tagId;

}
