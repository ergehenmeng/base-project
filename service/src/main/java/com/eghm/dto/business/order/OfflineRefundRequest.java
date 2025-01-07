package com.eghm.dto.business.order;

import com.eghm.annotation.Assign;
import com.eghm.convertor.YuanToCentDecoder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

/**
 * @author wyb
 * @since 2023/6/14
 */
@Data
public class OfflineRefundRequest {

    @Schema(description = "订单编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "订单编号不能为空")
    private String orderNo;

    @Schema(description = "游客id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "请选择要退款的游客")
    private List<Long> visitorList;

    @Schema(description = "退款金额", requiredMode = Schema.RequiredMode.REQUIRED)
    @Min(value = 1, message = "退款金额最少0.01元")
    @NotNull(message = "退款金额不能为空")
    @JsonDeserialize(using = YuanToCentDecoder.class)
    private Integer refundAmount;

    @Schema(description = "退款凭证(转账记录)", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "退款凭证不能为空")
    private String certificate;

    @Schema(description = "备注信息", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "备注信息不能为空")
    private String remark;

    @Assign
    @Schema(description = "用户id", hidden = true)
    private Long userId;

}
