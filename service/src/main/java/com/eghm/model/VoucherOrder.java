package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "餐饮商家id(冗余)")
    private Long restaurantId;

    @Schema(description = "餐饮券id")
    private Long voucherId;

    @Schema(description = "餐饮券名称")
    private String title;

    @Schema(description = "用户id")
    private Long memberId;

    @Schema(description = "订单编号")
    private String orderNo;

    @Schema(description = "已核销数量")
    private Integer useNum;

    @Schema(description = "封面图片")
    private String coverUrl;

    @Schema(description = "划线价")
    private Integer linePrice;

    @Schema(description = "销售价")
    private Integer salePrice;

    @Schema(description = "购买说明")
    private String depict;

    @Schema(description = "限购数量")
    private Integer quota;

    @Schema(description = "有效期购买之日起")
    private Integer validDays;

    @Schema(description = "生效时间(包含)")
    private LocalDate effectDate;

    @Schema(description = "失效日期(包含)")
    private LocalDate expireDate;

    @Schema(description = "使用开始时间")
    private String effectTime;

    @Schema(description = "使用截止时间")
    private String expireTime;

    @Schema(description = "详细介绍")
    private String introduce;
}
