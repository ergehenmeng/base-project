package com.eghm.dto.business.item;

import com.eghm.annotation.Assign;
import com.eghm.validation.annotation.OptionInt;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2022/7/1
 */
@Data
public class ItemActivityRequest {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "活动类型 1:拼团 2:限时购", required = true)
    @OptionInt(value = {1, 2}, message = "活动类型不能为空")
    private Integer activityType;

    @ApiModelProperty(value = "是否只读查询", required = true)
    private Boolean readonly;

    @ApiModelProperty(value = "商户id", hidden = true)
    @Assign
    private Long merchantId;
}
