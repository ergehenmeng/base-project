package com.eghm.vo.business.restaurant;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 二哥很猛
 * @since 2023/1/16
 */
@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class RestaurantVO {

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

    @Schema(description = "经度")
    private BigDecimal longitude;

    @Schema(description = "纬度")
    private BigDecimal latitude;

    @Schema(description = "商家热线")
    private String phone;

    @Schema(description = "距离,单位:m")
    private Integer distance;

    @Schema(description = "状态 0:下架 1:上架 2:强制下架")
    private Integer state;
}
