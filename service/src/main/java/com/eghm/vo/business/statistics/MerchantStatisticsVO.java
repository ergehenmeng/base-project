package com.eghm.vo.business.statistics;

import com.eghm.convertor.CentToYuanEncoder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2024/9/2
 */

@Data
public class MerchantStatisticsVO {

    @Schema(description = "商户名称")
    private String merchantName;

    @Schema(description = "销售额(含退款)")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer amount;
}
