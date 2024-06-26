package com.eghm.dto.poi;

import com.eghm.validation.annotation.WordChecker;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2023/12/20
 */

@Data
public class PoiPointEditRequest {

    @ApiModelProperty(value = "id", required = true)
    @NotNull(message = "id不能为空")
    private Long id;

    @ApiModelProperty(value = "点位名称", required = true)
    @Size(max = 20, message = "点位名称最大20字符")
    @NotBlank(message = "点位名称不能为空")
    @WordChecker(message = "点位名称存在敏感词")
    private String title;

    @ApiModelProperty(value = "区域", required = true)
    @NotBlank(message = "请选择区域")
    private String areaCode;

    @ApiModelProperty(value = "封面图", required = true)
    @NotEmpty(message = "封面图不能为空")
    private List<String> coverList;

    @ApiModelProperty(value = "点位类型", required = true)
    @NotNull(message = "请选择点位类型")
    private Long typeId;

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

    @ApiModelProperty(value = "详细地址", required = true)
    @NotBlank(message = "详细地址不能为空")
    @Size(max = 50, message = "详细地址最大50字符")
    @WordChecker(message = "详细地址存在敏感词")
    private String detailAddress;

    @ApiModelProperty(value = "详细介绍", required = true)
    @NotBlank(message = "详细介绍不能为空")
    @WordChecker(message = "详细介绍存在敏感词")
    private String introduce;
}
