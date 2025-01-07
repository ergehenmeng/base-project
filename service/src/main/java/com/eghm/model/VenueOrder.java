package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.enums.ref.VenueType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * <p>
 * 场馆预约订单表
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-02-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("venue_order")
public class VenueOrder extends BaseEntity {

    @Schema(description = "场馆id")
    private Long venueId;

    @Schema(description = "场地id")
    private Long venueSiteId;

    @Schema(description = "订单号")
    private String orderNo;

    @Schema(description = "下单人")
    private Long memberId;

    @Schema(description = "场馆名称")
    private String title;

    @Schema(description = "场地名称")
    private String siteTitle;

    @Schema(description = "场馆类型 (1:篮球馆 2:网球馆 3:羽毛球馆 4:乒乓球馆 5:游泳馆 6:健身馆 7:瑜伽馆 8:保龄馆 9:足球馆 10:排球馆 11:田径馆 12:综合馆 13:跆拳道馆)")
    private VenueType venueType;

    @Schema(description = "场馆封面图")
    private String coverUrl;

    @Schema(description = "所属商户")
    private Long merchantId;

    @Schema(description = "营业时间")
    private String openTime;

    @Schema(description = "省id")
    private Long provinceId;

    @Schema(description = "城市id")
    private Long cityId;

    @Schema(description = "县区id")
    private Long countyId;

    @Schema(description = "详细地址")
    private String detailAddress;

    @Schema(description = "经度")
    private BigDecimal longitude;

    @Schema(description = "纬度")
    private BigDecimal latitude;

    @Schema(description = "商家电话")
    private String telephone;

    @Schema(description = "预约日期")
    private LocalDate visitDate;

    @Schema(description = "预约的时间段及价格(json)")
    private String timePhase;

    @Schema(description = "场馆详细信息")
    private String introduce;

}
