package com.eghm.vo.business.restaurant;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 二哥很猛
 * @since 2023/1/16
 */

@Data
public class RestaurantDetailVO {

    @Schema(description = "id主键")
    private Long id;

    @Schema(description = "商家名称")
    private String title;

    @Schema(description = "商家logo")
    private String logoUrl;

    @Schema(description = "商家封面")
    private String coverUrl;

    @Schema(description = "营业时间")
    private String openTime;

    @Schema(description = "是否收藏")
    private Boolean collect;

    @Schema(description = "省份")
    @JsonIgnore
    private Long provinceId;

    @Schema(description = "城市id")
    @JsonIgnore
    private Long cityId;

    @Schema(description = "县区id")
    @JsonIgnore
    private Long countyId;

    @Schema(description = "详细地址")
    private String detailAddress;

    @Schema(description = "经度")
    private BigDecimal longitude;

    @Schema(description = "纬度")
    private BigDecimal latitude;

    @Schema(description = "距离,单位:m")
    private Integer distance;

    @Schema(description = "商家热线")
    private String phone;

    @Schema(description = "商家介绍")
    private String introduce;

    @Schema(description = "分数")
    private BigDecimal score;
}
