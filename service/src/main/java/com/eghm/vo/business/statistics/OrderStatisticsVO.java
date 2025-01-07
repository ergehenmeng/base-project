package com.eghm.vo.business.statistics;

import cn.hutool.core.util.RandomUtil;
import com.eghm.convertor.CentToYuanEncoder;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * @author 二哥很猛
 * @since 2024/1/22
 */

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderStatisticsVO {

    @Schema(description = "下单日期")
    @JsonFormat(pattern = "MM-dd")
    private LocalDate createDate;

    @Schema(description = "下单月份")
    private String createMonth;

    @Schema(description = "订单数量")
    private Integer orderNum = 0;

    @Schema(description = "支付金额")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer payAmount = 0;

    public OrderStatisticsVO(LocalDate createDate) {
        this.createDate = createDate;
        this.orderNum = RandomUtil.randomInt(100);
        this.payAmount = RandomUtil.randomInt(10000);
    }

    public OrderStatisticsVO(String createMonth) {
        this.createMonth = createMonth;
        this.orderNum = RandomUtil.randomInt(100);
        this.payAmount = RandomUtil.randomInt(10000);
    }
}
