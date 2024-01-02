package com.eghm.dto.poi;

import com.eghm.validation.annotation.WordChecker;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author 二哥很猛
 * @since 2023/12/20
 */

@Data
public class PoiTypeEditRequest {

    @ApiModelProperty(value = "id", required = true)
    @NotNull(message = "id不能为空")
    private Long id;

    @ApiModelProperty(value = "类型名称", required = true)
    @Size(max = 20, message = "类型名称最大20字符")
    @NotBlank(message = "类型名称不能为空")
    @WordChecker(message = "类型名称包含敏感词")
    private String title;

    @ApiModelProperty(value = "区域", required = true)
    @NotBlank(message = "请选择区域")
    private String areaCode;

    @ApiModelProperty(value = "icon", required = true)
    @NotBlank(message = "icon不能为空")
    private String icon;

    @ApiModelProperty(value = "排序")
    private Integer sort;
}
