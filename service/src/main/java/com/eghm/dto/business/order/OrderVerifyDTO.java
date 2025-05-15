package com.eghm.dto.business.order;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

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

    @Schema(description = "待核销的游客id")
    private List<Long> visitorList;

    @Schema(description = "套票票子订单id(只有门票订单且为套票票才需要该字段)")
    private Long combineId;

    @Schema(description = "备注信息")
    private String remark;
}
