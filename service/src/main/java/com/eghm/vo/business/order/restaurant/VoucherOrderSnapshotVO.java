package com.eghm.vo.business.order.restaurant;

import com.eghm.convertor.CentToYuanSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "图片")
    private String coverUrl;

    @Schema(description = "餐饮券名称")
    private String title;

    @Schema(description = "餐饮店名称")
    private String restaurantName;

    @Schema(description = "餐饮店logo")
    private String restaurantLogoUrl;

    @Schema(description = "餐饮店id")
    private Long restaurantId;

    @Schema(description = "划线价")
    @JsonSerialize(using = CentToYuanSerializer.class)
    private Integer linePrice;

    @Schema(description = "销售价")
    @JsonSerialize(using = CentToYuanSerializer.class)
    private Integer salePrice;

    @Schema(description = "购买说明")
    private String depict;

    @Schema(description = "有效期购买之日起(与开始日期和截止日期互斥)")
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
