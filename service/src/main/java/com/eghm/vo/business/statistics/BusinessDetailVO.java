package com.eghm.vo.business.statistics;

import cn.hutool.core.util.RandomUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2024/9/12
 */

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BusinessDetailVO {

    @ApiModelProperty("待发货订单")
    private Integer readyNum;

    @ApiModelProperty("退款数量中")
    private Integer refundNum;

    @ApiModelProperty("待核销订单")
    private Integer verifyNum;

    public BusinessDetailVO() {
        this.readyNum = RandomUtil.randomInt(1000);
        this.refundNum = RandomUtil.randomInt(500);
        this.verifyNum = RandomUtil.randomInt(100);
    }
}
