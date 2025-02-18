package com.eghm.vo.business.restaurant;

import com.eghm.convertor.CentToYuanOmitSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;

/**
 * <p>
 * 餐饮代金券
 * </p>
 *
 * @author 二哥很猛
 * @since 2022-06-30
 */
@Data
public class VoucherDetailResponse {

    @ApiModelProperty("id主键")
    private Long id;

    @ApiModelProperty(value = "餐饮商家id")
    private Long restaurantId;

    @ApiModelProperty(value = "商品名称")
    private String title;

    @ApiModelProperty(value = "封面图片")
    private String coverUrl;

    @ApiModelProperty(value = "划线价")
    @JsonSerialize(using = CentToYuanOmitSerializer.class)
    private Integer linePrice;

    @ApiModelProperty(value = "销售价")
    @JsonSerialize(using = CentToYuanOmitSerializer.class)
    private Integer salePrice;

    @ApiModelProperty(value = "剩余库存")
    private Integer stock;

    @ApiModelProperty(value = "虚拟销量")
    private Integer virtualNum;

    @ApiModelProperty(value = "购买说明")
    private String depict;

    @ApiModelProperty(value = "单次限购数量")
    private Integer quota;

    @ApiModelProperty(value = "有效期购买之日起(与开始日期和截止日期互斥)")
    private Integer validDays;

    @ApiModelProperty(value = "使用开始日期(包含) yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate effectDate;

    @ApiModelProperty(value = "使用截止日期(包含) yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate expireDate;

    @ApiModelProperty(value = "使用开始时间 HH:mm")
    private String effectTime;

    @ApiModelProperty(value = "使用截止时间 HH:mm")
    private String expireTime;

    @ApiModelProperty(value = "详细介绍")
    private String introduce;
}
