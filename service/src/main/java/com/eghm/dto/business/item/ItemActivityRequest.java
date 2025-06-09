package com.eghm.dto.business.item;

import com.eghm.annotation.Assign;
import com.eghm.validation.annotation.OptionInt;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2022/7/1
 */
@Data
public class ItemActivityRequest {

    @Schema(description = "id")
    private Long id;

    @Schema(description = "活动类型 1:拼团 2:限时购", requiredMode = Schema.RequiredMode.REQUIRED)
    @OptionInt(value = {1, 2}, message = "活动类型不能为空")
    private Integer activityType;

    @Schema(description = "是否只读查询", requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean readonly;

    @Schema(description = "商户id", hidden = true)
    @Assign
    private Long merchantId;
}
