package com.eghm.vo.business.venue;

import com.eghm.enums.VenueType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 二哥很猛
 * @since 2024/2/2
 */

@Data
public class VenueDetailResponse {

    @Schema(description = "id主键")
    private Long id;

    @Schema(description = "场馆名称")
    private String title;

    @Schema(description = "场馆类型 (1:篮球馆 2:网球馆 3:羽毛球馆 4:乒乓球馆 5:游泳馆 6:健身馆 7:瑜伽馆 8:保龄馆 9:足球馆 10:排球馆 11:田径馆 12:综合馆 13:跆拳道馆)")
    private VenueType venueType;

    @Schema(description = "场馆封面图")
    private String coverUrl;

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

    @Schema(description = "场馆详细信息")
    private String introduce;
}
