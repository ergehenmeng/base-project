package com.eghm.vo.business.order.adjust;

import com.eghm.convertor.CentToYuanEncoder;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @since 2024/3/25
 */

@Data
public class OrderAdjustResponse {

    @ApiModelProperty("商品名称")
    private String productName;

    @ApiModelProperty(value = "操作人姓名")
    private String userName;

    @ApiModelProperty(value = "原价格")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer sourcePrice;

    @ApiModelProperty(value = "新价格")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer targetPrice;

    @ApiModelProperty(value = "改价时间")
    @JsonFormat(pattern = "MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}
