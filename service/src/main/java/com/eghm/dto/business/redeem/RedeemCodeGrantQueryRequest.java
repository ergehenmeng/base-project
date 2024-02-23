package com.eghm.dto.business.redeem;

import com.eghm.dto.ext.PagingQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * @author 二哥很猛
 * @since 2024/2/11
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RedeemCodeGrantQueryRequest extends PagingQuery {

    @ApiModelProperty(value = "状态 0:待发放 1:已发放 2:已过期")
    private Integer state;

    @ApiModelProperty("兑换码配置id")
    @NotNull(message = "兑换码配置id不能为空")
    private Long redeemCodeId;
}
