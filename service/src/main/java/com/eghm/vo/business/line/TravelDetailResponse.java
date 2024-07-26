package com.eghm.vo.business.line;

import com.eghm.enums.ref.State;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 二哥很猛
 * @since 2024/6/5
 */

@Data
public class TravelDetailResponse {

    @ApiModelProperty("id主键")
    private Long id;

    @ApiModelProperty(value = "旅行社名称")
    private String title;

    @ApiModelProperty("商户id")
    private Long merchantId;

    @ApiModelProperty("店铺logo")
    private String logoUrl;

    @ApiModelProperty("旅行社电话")
    private String phone;

    @ApiModelProperty(value = "状态 0:待上架 1:已上架 2:强制下架")
    private State state;

    @ApiModelProperty(value = "省份id")
    private Long provinceId;

    @ApiModelProperty(value = "城市id")
    private Long cityId;

    @ApiModelProperty(value = "县区id")
    private Long countyId;

    @ApiModelProperty(value = "详细地址")
    private String detailAddress;

    @ApiModelProperty(value = "旅行社图片")
    private String coverUrl;

    @ApiModelProperty(value = "经度")
    private BigDecimal longitude;

    @ApiModelProperty(value = "纬度")
    private BigDecimal latitude;

    @ApiModelProperty(value = "旅行社描述信息")
    private String depict;

    @ApiModelProperty(value = "旅行社详细介绍信息")
    private String introduce;
}
