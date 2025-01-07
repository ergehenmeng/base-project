package com.eghm.dto.business.order;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author wyb
 * @since 2023/5/30
 */
@Data
public class OrderVerifyDTO {

    @Schema(description = "订单号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "订单号不能为空")
    private String orderNo;

    @Schema(description = "待核销的游客id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "请选择要核销的游客")
    private List<Long> visitorList;

    @Schema(description = "备注信息")
    private String remark;
}
