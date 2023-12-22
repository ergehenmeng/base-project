package com.eghm.dto.poi;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

/**
 * @author 二哥很猛
 * @since 2023/12/21
 */
@Data
public class PoiLineEditRequest {

    @ApiModelProperty(value = "id", required = true)
    @NotNull(message = "id不能为空")
    private Long id;

    @ApiModelProperty(value = "线路名称")
    @NotBlank(message = "线路名称不能为空")
    @Size(max = 20, message = "线路名称最大20字符")
    private String title;

    @ApiModelProperty(value = "所属区域编号")
    @NotBlank(message = "请选择区域")
    private String areaCode;

    @ApiModelProperty(value = "封面图")
    @NotBlank(message = "封面图不能为空")
    private String coverUrl;

    @ApiModelProperty(value = "预计游玩时间")
    @NotBlank(message = "预计游玩时间不能为空")
    private BigDecimal playTime;

    @ApiModelProperty(value = "详细介绍")
    @NotBlank(message = "详细介绍不能为空")
    private String introduce;
}
