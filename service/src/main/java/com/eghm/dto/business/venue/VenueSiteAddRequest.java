package com.eghm.dto.business.venue;

import com.eghm.validation.annotation.WordChecker;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2024/1/31
 */

@Data
public class VenueSiteAddRequest {

    @Schema(description = "场地名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @Size(min = 2, max = 20, message = "场地名称长度2~20位")
    @NotBlank(message = "场地名称不能为空")
    @WordChecker(message = "场地名称存在敏感词")
    private String title;

    @Schema(description = "场地封面图片", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "场地封面图片不能为空")
    private List<String> coverList;

    @Schema(description = "所属场馆", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "请选择所属场馆")
    private Long venueId;


}
