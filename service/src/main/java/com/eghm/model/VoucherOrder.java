package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * <p>
 * 餐饮券订单表
 * </p>
 *
 * @author 二哥很猛
 * @since 2022-08-23
 */
@Data
@TableName("voucher_order")
@EqualsAndHashCode(callSuper = true)
public class VoucherOrder extends BaseEntity {

    @ApiModelProperty(value = "餐饮商家id(冗余)")
    private Long restaurantId;

    @ApiModelProperty(value = "餐饮券id")
    private Long voucherId;

    @ApiModelProperty("餐饮券名称")
    private String title;

    @ApiModelProperty(value = "用户id")
    private Long memberId;

    @ApiModelProperty(value = "订单编号")
    private String orderNo;

    @ApiModelProperty("已核销数量")
    private Integer useNum;

    @ApiModelProperty(value = "封面图片")
    private String coverUrl;

    @ApiModelProperty(value = "划线价")
    private Integer linePrice;

    @ApiModelProperty(value = "销售价")
    private Integer salePrice;

    @ApiModelProperty(value = "购买说明")
    private String depict;

    @ApiModelProperty(value = "限购数量")
    private Integer quota;

    @ApiModelProperty(value = "有效期购买之日起")
    private Integer validDays;

    @ApiModelProperty(value = "生效时间(包含)")
    private LocalDate effectDate;

    @ApiModelProperty(value = "失效日期(包含)")
    private LocalDate expireDate;

    @ApiModelProperty(value = "使用开始时间")
    private String effectTime;

    @ApiModelProperty(value = "使用截止时间")
    private String expireTime;

    @ApiModelProperty(value = "详细介绍")
    private String introduce;
}
