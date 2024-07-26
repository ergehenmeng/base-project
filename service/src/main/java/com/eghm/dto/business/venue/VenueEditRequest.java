package com.eghm.dto.business.venue;

import com.eghm.enums.ref.VenueType;
import com.eghm.validation.annotation.Phone;
import com.eghm.validation.annotation.WordChecker;
import com.google.gson.annotations.Expose;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2024/1/31
 */

@Data
public class VenueEditRequest {

    @ApiModelProperty(value = "id", required = true)
    @NotNull(message = "id不能为空")
    private Long id;

    @ApiModelProperty(value = "场馆名称", required = true)
    @Size(min = 2, max = 20, message = "场馆名称长度2~20位")
    @NotBlank(message = "场馆名称不能为空")
    @WordChecker(message = "场馆名称存在敏感词")
    private String title;

    @ApiModelProperty(value = "场馆类型", required = true)
    @NotNull(message = "场馆类型不能为空")
    private VenueType venueType;

    @ApiModelProperty(value = "封面图", required = true)
    @NotEmpty(message = "场馆封面图不能为空")
    private List<String> coverList;

    @ApiModelProperty(value = "营业时间", required = true)
    @NotBlank(message = "营业时间不能为空")
    private String openTime;

    @ApiModelProperty(value = "省id", required = true)
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
    @WordChecker(message = "详细地址存在敏感词")
    private String detailAddress;

    @ApiModelProperty(value = "经度", required = true)
    @NotNull(message = "经度不能为空")
    @DecimalMin(value = "-180", message = "经度应(-180, 180]范围内", inclusive = false)
    @DecimalMax(value = "180", message = "经度应(-180, 180]范围内")
    private BigDecimal longitude;

    @ApiModelProperty(value = "纬度", required = true)
    @NotNull(message = "纬度不能为空")
    private BigDecimal latitude;

    @ApiModelProperty(value = "客服电话", required = true)
    @Phone(message = "客服电话格式错误")
    private String telephone;

    @ApiModelProperty(value = "场馆介绍", required = true)
    @NotBlank(message = "场馆介绍不能为空")
    @WordChecker(message = "场馆介绍存在敏感词")
    @Expose(serialize = false)
    private String introduce;
}
