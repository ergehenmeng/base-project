package com.eghm.vo.business.statistics;

import cn.hutool.core.util.RandomUtil;
import com.eghm.convertor.CentToYuanSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty("下单日期")
    @JsonFormat(pattern = "MM-dd")
    private LocalDate createDate;

    @ApiModelProperty("下单月份")
    private String createMonth;

    @ApiModelProperty("订单数量")
    private Integer orderNum;

    @ApiModelProperty("支付金额")
    @JsonSerialize(using = CentToYuanSerializer.class)
    private Integer payAmount;

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
