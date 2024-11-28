package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * <p>
 * 组合票订单表
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-10-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ticket_order_snapshot")
public class TicketOrderSnapshot extends BaseEntity {

    @ApiModelProperty(value = "门票所属景区id")
    private Long scenicId;

    @ApiModelProperty(value = "订单编号")
    private String orderNo;

    @ApiModelProperty(value = "划线价")
    private Integer linePrice;

    @ApiModelProperty(value = "销售价")
    private Integer salePrice;

    @ApiModelProperty(value = "门票种类 1:成人 2:老人 3:儿童  4:演出 5:活动 6:研学")
    private Integer category;

    @ApiModelProperty(value = "是否实名购票 0:不实名 1:实名")
    private Boolean realBuy;

    @ApiModelProperty(value = "门票介绍")
    private String introduce;

    @ApiModelProperty(value = "门票id")
    private Long ticketId;

    @ApiModelProperty(value = "使用时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime useTime;

}
