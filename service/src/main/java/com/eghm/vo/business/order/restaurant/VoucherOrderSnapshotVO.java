package com.eghm.vo.business.order.restaurant;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;

/**
 * 门票订单列表vo
 *
 * @author 二哥很猛
 * @since 2023/7/28
 */
@Data
public class VoucherOrderSnapshotVO {

    @ApiModelProperty("图片")
    private String coverUrl;

    @ApiModelProperty("餐饮券名称")
    private String title;

    @ApiModelProperty("餐饮店名称")
    private String restaurantName;

    @ApiModelProperty("餐饮店logo")
    private String restaurantLogoUrl;

    @ApiModelProperty("餐饮店id")
    private Long restaurantId;

    @ApiModelProperty(value = "划线价")
    private Integer linePrice;

    @ApiModelProperty(value = "销售价")
    private Integer salePrice;

    @ApiModelProperty(value = "购买说明")
    private String depict;

    @ApiModelProperty(value = "有效期购买之日起(与开始日期和截止日期互斥)")
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
