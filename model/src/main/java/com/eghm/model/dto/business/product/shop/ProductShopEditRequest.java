package com.eghm.model.dto.business.product.shop;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

/**
 * @author 二哥很猛
 * @date 2022/7/1
 */

@Data
public class ProductShopEditRequest {

    @ApiModelProperty("id")
    @NotNull(message = "id不能为空")
    private Long id;

    @ApiModelProperty("店铺名称")
    @Size(min = 2, max = 20, message = "店铺名称2~20字符")
    private String title;

    @ApiModelProperty(value = "店铺logo")
    @NotBlank(message = "店铺logo不能为空")
    private String logoUrl;

    @ApiModelProperty(value = "营业时间")
    @NotBlank(message = "营业时间不能为空")
    private String openTime;

    @ApiModelProperty(value = "省id")
    @NotNull(message = "省份不能为空")
    private Long provinceId;

    @ApiModelProperty(value = "城市id")
    @NotNull(message = "城市不能为空")
    private Long cityId;

    @ApiModelProperty(value = "县区id")
    @NotNull(message = "县区不能为空")
    private Long countyId;

    @ApiModelProperty(value = "详细地址")
    @NotBlank(message = "详细地址不能为空")
    private String detailAddress;

    @ApiModelProperty(value = "经度")
    @NotNull(message = "经度不能为空")
    private BigDecimal longitude;

    @ApiModelProperty(value = "纬度")
    @NotNull(message = "纬度不能为空")
    private BigDecimal latitude;

    @ApiModelProperty(value = "商家电话")
    @NotBlank(message = "商家电话不能为空")
    private String telephone;

    @ApiModelProperty(value = "商家介绍")
    @NotBlank(message = "商家介绍不能为空")
    private String introduce;
}
