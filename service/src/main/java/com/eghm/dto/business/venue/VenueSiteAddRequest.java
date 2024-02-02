package com.eghm.dto.business.venue;

import com.eghm.validation.annotation.WordChecker;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author 二哥很猛
 * @since 2024/1/31
 */

@Data
public class VenueSiteAddRequest {

    @ApiModelProperty(value = "场地名称", required = true)
    @Size(min = 2, max = 20, message = "场地名称长度2~20位")
    @NotBlank(message = "场地名称不能为空")
    @WordChecker(message = "场地名称存在敏感词")
    private String title;

    @ApiModelProperty(value = "所属场馆", required = true)
    @NotNull(message = "请选择所属场馆")
    private Long venueId;

}
