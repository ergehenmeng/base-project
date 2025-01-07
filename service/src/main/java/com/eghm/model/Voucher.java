package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.convertor.CentToYuanEncoder;
import com.eghm.enums.ref.State;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
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
@TableName("voucher")
@EqualsAndHashCode(callSuper = true)
public class Voucher extends BaseEntity {

    @Schema(description = "餐饮商家id")
    private Long restaurantId;

    @Schema(description = "所属商户id")
    private Long merchantId;

    @Schema(description = "状态 0:待上架 1:已上架 2:强制下架")
    private State state;

    @Schema(description = "是否为热销商品 true:是 false:不是")
    private Boolean hotSell;

    @Schema(description = "商品名称")
    private String title;

    @Schema(description = "封面图片")
    private String coverUrl;

    @Schema(description = "标签id")
    private Long tagId;

    @Schema(description = "划线价")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer linePrice;

    @Schema(description = "销售价")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer salePrice;

    @Schema(description = "剩余库存")
    private Integer stock;

    @Schema(description = "销售数量")
    private Integer saleNum;

    @Schema(description = "总销量=虚拟销量+真实销量")
    private Integer totalNum;

    @Schema(description = "评分")
    private BigDecimal score;

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

    @Schema(description = "创建日期")
    private LocalDate createDate;

    @Schema(description = "创建月份")
    private String createMonth;
}
