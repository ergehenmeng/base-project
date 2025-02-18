package com.eghm.vo.business.statistics;

import com.eghm.convertor.CentToYuanSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2024/9/2
 */

@Data
public class MerchantStatisticsVO {

    @ApiModelProperty("商户名称")
    private String merchantName;

    @ApiModelProperty("销售额(含退款)")
    @JsonSerialize(using = CentToYuanSerializer.class)
    private Integer amount;
}
