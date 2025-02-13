package com.eghm.dto.business.pay;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
* @author 二哥很猛
* @since 2024-04-15
*/
@Data
public class PayConfigEditRequest {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "id不能为空")
    private Long id;

    @Schema(description = "是否开启微信支付", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "是否开启微信支付不能为空")
    private Boolean wechatPay;

    @Schema(description = "是否开启支付宝支付", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "是否开启支付宝支付不能为空")
    private Boolean aliPay;

    @Schema(description = "备注信息")
    @Length(max = 200, message = "备注信息最大200字符")
    private String remark;

}
