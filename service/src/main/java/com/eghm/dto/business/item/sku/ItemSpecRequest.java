package com.eghm.dto.business.item.sku;

import com.eghm.enums.ref.SpecLevel;
import com.eghm.validation.annotation.WordChecker;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

/**
 * @author 殿小二
 * @since 2023/3/6
 */

@Data
public class ItemSpecRequest {

    @Schema(description = "规格名", requiredMode = Schema.RequiredMode.REQUIRED)
    @Size(min = 1, max = 20, message = "规格名长度1~20位")
    @NotBlank(message = "规格名不能为空")
    @WordChecker(message = "规格名存在敏感词")
    private String specName;

    @Schema(description = "规格值", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "规格值不能为空")
    private List<SpecValue> valueList;

    @Schema(description = "规格等级(1:一级规格 2:二级规格)", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "规格等级不能为空")
    private SpecLevel level;

    @Data
    public static class SpecValue {

        @Schema(description = "规格值id(编辑时不能为空)")
        private Long id;

        @Schema(description = "规格值", requiredMode = Schema.RequiredMode.REQUIRED)
        @Size(min = 1, max = 20, message = "规格值长度1~20位")
        @NotBlank(message = "规格值不能为空")
        @WordChecker(message = "规格值存在敏感词")
        private String name;

        @Schema(description = "规格图片(一级规格必填), 优先级比sku_pic低", requiredMode = Schema.RequiredMode.REQUIRED)
        private String pic;
    }
}
