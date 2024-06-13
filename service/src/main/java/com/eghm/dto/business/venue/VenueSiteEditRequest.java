package com.eghm.dto.business.venue;

import com.eghm.validation.annotation.WordChecker;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2024/1/31
 */

@Data
public class VenueSiteEditRequest {

    @ApiModelProperty(value = "id", required = true)
    @NotNull(message = "id不能为空")
    private Long id;

    @ApiModelProperty(value = "场地名称", required = true)
    @Size(min = 2, max = 20, message = "场地名称长度2~20位")
    @NotBlank(message = "场地名称不能为空")
    @WordChecker(message = "场地名称存在敏感词")
    private String title;

    @ApiModelProperty(value = "场地封面图片", required = true)
    @NotEmpty(message = "场地封面图片不能为空")
    private List<String> coverList;

    @ApiModelProperty(value = "所属场馆", required = true)
    @NotNull(message = "请选择所属场馆")
    private Long venueId;

}
