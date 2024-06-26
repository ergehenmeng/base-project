package com.eghm.dto.poi;

import com.eghm.validation.annotation.WordChecker;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2023/12/21
 */
@Data
public class PoiLineAddRequest {

    @ApiModelProperty(value = "线路名称", required = true)
    @NotBlank(message = "线路名称不能为空")
    @Size(max = 20, message = "线路名称最大20字符")
    @WordChecker(message = "线路名称存在敏感词")
    private String title;

    @ApiModelProperty(value = "所属区域编号", required = true)
    @NotBlank(message = "请选择区域")
    private String areaCode;

    @ApiModelProperty(value = "封面图", required = true)
    @NotEmpty(message = "封面图不能为空")
    private List<String> coverList;

    @ApiModelProperty(value = "预计游玩时间", required = true)
    @NotNull(message = "预计游玩时间不能为空")
    private BigDecimal playTime;

    @ApiModelProperty(value = "详细介绍", required = true)
    @NotBlank(message = "详细介绍不能为空")
    @WordChecker(message = "详细介绍存在敏感词")
    private String introduce;
}
