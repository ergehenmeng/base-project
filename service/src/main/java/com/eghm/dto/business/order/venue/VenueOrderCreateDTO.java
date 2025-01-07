package com.eghm.dto.business.order.venue;

import com.eghm.validation.annotation.Mobile;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

/**
 * @author wyb
 * @since 2024/2/4
 */
@Data
public class VenueOrderCreateDTO {

    @Schema(description = "场次id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "请选择场次")
    private Long venueSiteId;

    @Schema(description = "时间段id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "请选择时间段")
    private List<Long> phaseList;

    @Schema(description = "优惠券id")
    private Long couponId;

    @Schema(description = "联系人姓名", requiredMode = Schema.RequiredMode.REQUIRED)
    @Size(max = 10, message = "联系人姓名最大10字符")
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
