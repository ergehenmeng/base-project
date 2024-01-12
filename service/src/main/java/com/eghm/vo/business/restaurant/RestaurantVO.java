package com.eghm.vo.business.restaurant;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 二哥很猛
 * @since 2023/1/16
 */
@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class RestaurantVO {

    @ApiModelProperty("id主键")
    private Long id;

    @ApiModelProperty(value = "商家名称")
    private String title;

    @ApiModelProperty("商家logo")
    private String logoUrl;

    @ApiModelProperty(value = "商家封面")
    private String coverUrl;

    @ApiModelProperty(value = "营业时间")
    private String openTime;

    @ApiModelProperty(value = "经度")
    private BigDecimal longitude;

    @ApiModelProperty(value = "纬度")
    private BigDecimal latitude;

    @ApiModelProperty(value = "商家热线")
    private String phone;

    @ApiModelProperty("距离,单位:m")
    private Integer distance;

    @ApiModelProperty("状态 0:下架 1:上架 2:强制下架")
    private Integer state;
}
