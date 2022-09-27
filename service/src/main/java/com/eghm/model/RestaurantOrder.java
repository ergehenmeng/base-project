package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
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
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("restaurant_order")
@ApiModel(value="RestaurantOrder对象", description="餐饮券订单表")
public class RestaurantOrder extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "餐饮商家id(冗余)")
    private Long restaurantId;

    @ApiModelProperty(value = "餐饮券id")
    private Long voucherId;

    @ApiModelProperty(value = "订单编号")
    private String orderNo;

    @ApiModelProperty(value = "封面图片")
    private String coverUrl;

    @ApiModelProperty(value = "划线价")
    private Integer linePrice;

    @ApiModelProperty(value = "销售价")
    private Integer salePrice;

    @ApiModelProperty(value = "购买说明")
    private String describe;

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
