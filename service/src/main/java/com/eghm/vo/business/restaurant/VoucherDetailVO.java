package com.eghm.vo.business.restaurant;

import com.eghm.convertor.CentToYuanSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;

/**
 * @author 二哥很猛
 * @since 2023/10/24
 */
@Data
public class VoucherDetailVO {

    @Schema(description = "id")
    private Long id;

    @Schema(description = "是否为热销商品 true:是 false:不是")
    private Boolean hotSell;

    @Schema(description = "商品名称")
    private String title;

    @Schema(description = "封面图片")
    private String coverUrl;

    @Schema(description = "划线价")
    @JsonSerialize(using = CentToYuanSerializer.class)
    private Integer linePrice;

    @Schema(description = "销售价")
    @JsonSerialize(using = CentToYuanSerializer.class)
    private Integer salePrice;

    @Schema(description = "剩余库存")
    private Integer stock;

    @Schema(description = "销售数量")
    private Integer totalNum;

    @Schema(description = "购买说明")
    private String depict;

    @Schema(description = "单次限购数量")
    private Integer quota;

    @Schema(description = "有效期购买之日起(与开始日期和截止日期互斥)")
    private Integer validDays;

    @Schema(description = "使用开始日期(包含) yyyy-MM-dd")
    private LocalDate effectDate;

    @Schema(description = "使用截止日期(包含) yyyy-MM-dd")
    private LocalDate expireDate;

    @Schema(description = "使用开始时间 HH:mm")
    private String effectTime;

    @Schema(description = "使用截止时间 HH:mm")
    private String expireTime;

    @Schema(description = "详细介绍")
    private String introduce;
}
