package com.eghm.dto.business.venue;

import com.eghm.enums.ref.VenueType;
import com.eghm.validation.annotation.Phone;
import com.eghm.validation.annotation.WordChecker;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.*;
import java.math.BigDecimal;

/**
 * @author 二哥很猛
 * @since 2024/1/31
 */

@Data
public class VenueEditRequest {

    @ApiModelProperty("id")
    @NotNull(message = "id不能为空")
    private Long id;

    @ApiModelProperty("场馆名称")
    @Size(min = 2, max = 20, message = "场馆名称长度2~20位")
    @NotBlank(message = "场馆名称不能为空")
    @WordChecker(message = "场馆名称存在敏感词")
    private String title;

    @ApiModelProperty("场馆类型")
    @NotNull(message = "场馆类型不能为空")
    private VenueType venueType;

    @ApiModelProperty("封面图")
    @NotBlank(message = "场馆封面图不能为空")
    private String coverUrl;

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
    @WordChecker(message = "详细地址存在敏感词")
    private String detailAddress;

    @ApiModelProperty(value = "经度")
    @NotNull(message = "经度不能为空")
    @DecimalMin(value = "-180", message = "经度应(-180, 180]范围内", inclusive = false)
    @DecimalMax(value = "180", message = "经度应(-180, 180]范围内")
    private BigDecimal longitude;

    @ApiModelProperty(value = "纬度")
    @NotNull(message = "纬度不能为空")
    private BigDecimal latitude;

    @ApiModelProperty(value = "商家电话")
    @Phone(message = "商家电话格式错误")
    private String telephone;

    @ApiModelProperty(value = "商家介绍")
    @NotBlank(message = "商家介绍不能为空")
    @WordChecker(message = "商家介绍存在敏感词")
    private String introduce;
}
