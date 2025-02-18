package com.eghm.vo.business.restaurant;

import com.eghm.convertor.CentToYuanSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;

/**
 * @author 二哥很猛
 * @since 2023/10/24
 */
@Data
public class VoucherDetailVO {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("是否为热销商品 true:是 false:不是")
    private Boolean hotSell;

    @ApiModelProperty(value = "商品名称")
    private String title;

    @ApiModelProperty(value = "封面图片")
    private String coverUrl;

    @ApiModelProperty(value = "划线价")
    @JsonSerialize(using = CentToYuanSerializer.class)
    private Integer linePrice;

    @ApiModelProperty(value = "销售价")
    @JsonSerialize(using = CentToYuanSerializer.class)
    private Integer salePrice;

    @ApiModelProperty(value = "剩余库存")
    private Integer stock;

    @ApiModelProperty(value = "销售数量")
    private Integer totalNum;

    @ApiModelProperty(value = "购买说明")
    private String depict;

    @ApiModelProperty(value = "单次限购数量")
    private Integer quota;

    @ApiModelProperty(value = "有效期购买之日起(与开始日期和截止日期互斥)")
    private Integer validDays;

    @ApiModelProperty(value = "使用开始日期(包含) yyyy-MM-dd")
    private LocalDate effectDate;

    @ApiModelProperty(value = "使用截止日期(包含) yyyy-MM-dd")
    private LocalDate expireDate;

    @ApiModelProperty(value = "使用开始时间 HH:mm")
    private String effectTime;

    @ApiModelProperty(value = "使用截止时间 HH:mm")
    private String expireTime;

    @ApiModelProperty(value = "详细介绍")
    private String introduce;
}
