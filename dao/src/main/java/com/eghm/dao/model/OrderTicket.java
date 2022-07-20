package com.eghm.dao.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.common.convertor.CentToYuanEncoder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 门票订单表
 * </p>
 *
 * @author 二哥很猛
 * @since 2022-07-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("order_ticket")
@ApiModel(value="OrderTicket对象", description="门票订单表")
public class OrderTicket extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "门票名称")
    private String title;

    @ApiModelProperty(value = "门票单价")
    private Integer price;

    @ApiModelProperty(value = "是否支持退款 0:不支持 1:支持")
    private Boolean supportRefund;

    @ApiModelProperty(value = "购买数量")
    private Integer num;

    @ApiModelProperty(value = "订单号")
    private String orderNo;

    @ApiModelProperty(value = "生效日期(包含)")
    private LocalDateTime effectiveDate;

    @ApiModelProperty(value = "失效日期(包含)")
    private LocalDateTime expireDate;

    @ApiModelProperty(value = "使用范围: 1:周一 2:周二 4:周三 8:周四 16:周五 32:周六 64:周日")
    private Integer useScope;

    @ApiModelProperty(value = "核销方式 1:手动核销 2:自动核销 (凌晨自动核销)")
    private Integer verificationType;

    @ApiModelProperty(value = "是否实名购票 0:不实名 1:实名")
    private Boolean realBuy;

    @ApiModelProperty(value = "门票介绍")
    private String introduce;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "门票id")
    private Long ticketId;

    @ApiModelProperty(value = "订单状态 0:待支付 1:支付成功 2:支付处理中")
    private Integer state;

    @ApiModelProperty(value = "联系人手机号")
    private String mobile;

    @ApiModelProperty("付款金额=单价*数量-优惠金额")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer payAmount;

    @ApiModelProperty(value = "优惠金额")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer discountAmount;

    @ApiModelProperty(value = "优惠券id")
    private Long couponId;

}
