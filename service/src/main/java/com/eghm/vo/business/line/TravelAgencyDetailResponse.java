package com.eghm.vo.business.line;

import com.eghm.enums.ref.State;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author 二哥很猛
 * @since 2024/6/5
 */

@Data
public class TravelAgencyDetailResponse {

    @ApiModelProperty("id主键")
    private Long id;

    @ApiModelProperty(value = "旅行社名称")
    private String title;

    @ApiModelProperty("店铺logo")
    private String logoUrl;

    @ApiModelProperty("旅行社电话")
    private String phone;

    @ApiModelProperty(value = "状态 0:待上架 1:已上架 2:强制下架")
    private State state;

    @ApiModelProperty(value = "城市id")
    private Long cityId;

    @ApiModelProperty(value = "县区id")
    private Long countyId;

    @ApiModelProperty(value = "详细地址")
    private String detailAddress;

    @ApiModelProperty(value = "旅行社图片")
    private String coverUrl;

    @ApiModelProperty(value = "经度")
    @NotNull(message = "经度不能能为空")
    private BigDecimal longitude;

    @ApiModelProperty(value = "纬度")
    @NotNull(message = "维度不能为空")
    private BigDecimal latitude;

    @ApiModelProperty(value = "旅行社描述信息")
    @NotBlank(message = "描述信息不能为空")
    private String depict;

    @ApiModelProperty(value = "旅行社详细介绍信息")
    @NotBlank(message = "旅行社详细介绍不能为空")
    private String introduce;
}
