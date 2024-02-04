package com.eghm.model;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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

    @ApiModelProperty(value = "场馆id")
    private Long venueId;

    @ApiModelProperty(value = "场地id")
    private Long venueSiteId;

    @ApiModelProperty(value = "下单人")
    private Long memberId;

    @ApiModelProperty(value = "场馆名称")
    private String title;

    @ApiModelProperty(value = "场地名称")
    private String siteTitle;

    @ApiModelProperty(value = "场馆类型")
    private Integer venueType;

    @ApiModelProperty(value = "场馆封面图")
    private String coverUrl;

    @ApiModelProperty(value = "所属商户")
    private Long merchantId;

    @ApiModelProperty(value = "营业时间")
    private String openTime;

    @ApiModelProperty(value = "省id")
    private Long provinceId;

    @ApiModelProperty(value = "城市id")
    private Long cityId;

    @ApiModelProperty(value = "县区id")
    private Long countyId;

    @ApiModelProperty(value = "详细地址")
    private String detailAddress;

    @ApiModelProperty(value = "经度")
    private BigDecimal longitude;

    @ApiModelProperty(value = "纬度")
    private BigDecimal latitude;

    @ApiModelProperty(value = "商家电话")
    private String telephone;

    @ApiModelProperty(value = "预约日期")
    private LocalDate visitDate;

    @ApiModelProperty(value = "预约的时间段及价格(json)")
    private String timePhase;

    @ApiModelProperty(value = "场馆详细信息")
    private String introduce;

}
