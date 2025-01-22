package com.eghm.vo.business.line;

import com.eghm.enums.State;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 二哥很猛
 * @since 2024/6/5
 */

@Data
public class TravelDetailResponse {

    @Schema(description = "id主键")
    private Long id;

    @Schema(description = "旅行社名称")
    private String title;

    @Schema(description = "商户id")
    private Long merchantId;

    @Schema(description = "店铺logo")
    private String logoUrl;

    @Schema(description = "旅行社电话")
    private String phone;

    @Schema(description = "状态 0:待上架 1:已上架 2:强制下架")
    private State state;

    @Schema(description = "省份id")
    private Long provinceId;

    @Schema(description = "城市id")
    private Long cityId;

    @Schema(description = "县区id")
    private Long countyId;

    @Schema(description = "详细地址")
    private String detailAddress;

    @Schema(description = "旅行社图片")
    private String coverUrl;

    @Schema(description = "经度")
    private BigDecimal longitude;

    @Schema(description = "纬度")
    private BigDecimal latitude;

    @Schema(description = "旅行社描述信息")
    private String depict;

    @Schema(description = "旅行社详细介绍信息")
    private String introduce;
}
