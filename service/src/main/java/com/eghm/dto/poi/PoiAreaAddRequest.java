package com.eghm.dto.poi;

import com.eghm.validation.annotation.WordChecker;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.*;
import java.math.BigDecimal;

/**
 * @author 二哥很猛
 * @since 2023/12/20
 */

@Data
public class PoiAreaAddRequest {

    @ApiModelProperty(value = "区域名称", required = true)
    @Size(max = 20, message = "区域名称最大20字符")
    @NotBlank(message = "区域名称不能为空")
    @WordChecker(message = "区域名称存在敏感词")
    private String title;

    @ApiModelProperty(value = "区域编号", required = true)
    @Size(max = 20, message = "区域编号最大20字符")
    @NotBlank(message = "区域编号不能为空")
    private String code;

    @ApiModelProperty(value = "经度", required = true)
    @DecimalMin(value = "-180", message = "经度应(-180, 180]范围内", inclusive = false)
    @DecimalMax(value = "180", message = "经度应(-180, 180]范围内")
    private BigDecimal longitude;

    @ApiModelProperty(value = "维度", required = true)
    @DecimalMin(value = "-90", message = "纬度应[-90, 90]范围内")
    @DecimalMax(value = "90", message = "纬度应[-90, 90]范围内")
    private BigDecimal latitude;

    @ApiModelProperty(value = "省份id", required = true)
    @NotNull(message = "省份不能为空")
    private Long provinceId;

    @ApiModelProperty(value = "城市id", required = true)
    @NotNull(message = "城市不能为空")
    private Long cityId;

    @ApiModelProperty(value = "区县id", required = true)
    @NotNull(message = "区县不能为空")
    private Long countyId;

    @ApiModelProperty(value = "详细地址", required = true)
    @Size(max = 100, message = "详细地址最大100字符")
    @NotBlank(message = "详细地址不能为空")
    @WordChecker(message = "详细地址存在敏感词")
    private String detailAddress;

    @ApiModelProperty(value = "备注信息")
    @WordChecker(message = "备注信息存在敏感词")
    private String remark;
}
