package com.eghm.dto.business.order.voucher;

import com.eghm.validation.annotation.Mobile;
import com.eghm.validation.annotation.RangeInt;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * @author wyb
 * @since 2023/5/8
 */
@Data
public class VoucherOrderCreateDTO {

    @Schema(description = "餐饮券id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "餐饮券不能为空")
    private Long voucherId;

    @RangeInt(min = 1, max = 99, message = "购买数量应为1~99张")
    @Schema(description = "数量", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer num;

    @Schema(description = "优惠券id")
    private Long couponId;

    @Schema(description = "联系人姓名", requiredMode = Schema.RequiredMode.REQUIRED)
    @Size(max = 10, message = "联系人姓名最大10字符")
    @NotBlank(message = "请输入联系人姓名")
    private String nickName;

    @Schema(description = "联系人电话", requiredMode = Schema.RequiredMode.REQUIRED)
    @Mobile(message = "联系人手机号格式错误")
    private String mobile;

    @Schema(description = "兑换码")
    @Size(max = 20, message = "兑换码最大20字符")
    private String cdKey;

    @Schema(description = "备注")
    @Size(max = 100, message = "备注最大100字符")
    private String remark;
}
