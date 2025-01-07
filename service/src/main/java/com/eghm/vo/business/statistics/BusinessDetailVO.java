package com.eghm.vo.business.statistics;

import cn.hutool.core.util.RandomUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2024/9/12
 */

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BusinessDetailVO {

    @Schema(description = "待发货订单")
    private Integer readyNum = 0;

    @Schema(description = "退款数量中")
    private Integer refundNum = 0;

    @Schema(description = "待核销订单")
    private Integer verifyNum = 0;

    public void setReadyNum(Integer readyNum) {
        this.readyNum = RandomUtil.randomInt(1000);
    }

    public void setRefundNum(Integer refundNum) {
        this.refundNum = RandomUtil.randomInt(500);
    }

    public void setVerifyNum(Integer verifyNum) {
        this.verifyNum = RandomUtil.randomInt(100);
    }
}
