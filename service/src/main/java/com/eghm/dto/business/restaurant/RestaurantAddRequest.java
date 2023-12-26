package com.eghm.dto.business.restaurant;

import com.eghm.validation.annotation.WordChecker;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.*;
import java.math.BigDecimal;

/**
 * @author 二哥很猛
 * @date 2022/6/30
 */
@Data
public class RestaurantAddRequest {

    @ApiModelProperty(value = "商家名称", required = true)
    @Size(min = 2, max = 20, message = "商家名称长度应为2~20位")
    @NotBlank(message = "商家名称不能为空")
    @WordChecker(message = "商家名称存在敏感词")
    private String title;

    @ApiModelProperty(value = "商家logo", required = true)
    @NotBlank(message = "logo不能为空")
    private String logoUrl;

    @ApiModelProperty(value = "商家封面", required = true)
    @NotBlank(message = "封面图片不能为空")
    private String coverUrl;

    @ApiModelProperty(value = "营业时间", required = true)
    @NotBlank(message = "营业时间不能为空")
    private String openTime;

    @ApiModelProperty(value = "省份", required = true)
    @NotNull(message = "省份不能为空")
    private Long provinceId;

    @ApiModelProperty(value = "城市id", required = true)
    @NotNull(message = "城市不能为空")
    private Long cityId;

    @ApiModelProperty(value = "县区id", required = true)
    @NotNull(message = "县区不能为空")
    private Long countyId;

    @ApiModelProperty(value = "详细地址", required = true)
    @NotBlank(message = "详细地址不能为空")
    @Size(max = 20, message = "详细地址长度2~20字符")
    @WordChecker(message = "详细地址存在敏感词")
    private String detailAddress;

    @ApiModelProperty(value = "经度", required = true)
    @NotNull(message = "经度不能为空")
    @DecimalMin(value = "-180", message = "经度应(-180, 180]范围内", inclusive = false)
    @DecimalMax(value = "180", message = "经度应(-180, 180]范围内")
    private BigDecimal longitude;

    @ApiModelProperty(value = "纬度", required = true)
    @NotNull(message = "纬度不能为空")
    @DecimalMin(value = "-90", message = "纬度应[-90, 90]范围内")
    @DecimalMax(value = "90", message = "纬度应[-90, 90]范围内")
    private BigDecimal latitude;

    @ApiModelProperty(value = "商家热线", required = true)
    @NotNull(message = "商家热线不能为空")
    private String phone;

    @ApiModelProperty(value = "商家介绍", required = true)
    @NotNull(message = "商家介绍不能为空")
    @WordChecker
    private String introduce;
}
