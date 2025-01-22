package com.eghm.dto.business.item.sku;

import com.eghm.enums.SpecLevel;
import com.eghm.validation.annotation.WordChecker;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author 殿小二
 * @since 2023/3/6
 */

@Data
public class ItemSpecRequest {

    @ApiModelProperty(value = "规格名", required = true)
    @Size(min = 1, max = 20, message = "规格名长度1~20位")
    @NotBlank(message = "规格名不能为空")
    @WordChecker(message = "规格名存在敏感词")
    private String specName;

    @ApiModelProperty(value = "规格值", required = true)
    @NotEmpty(message = "规格值不能为空")
    private List<SpecValue> valueList;

    @ApiModelProperty(value = "规格等级(1:一级规格 2:二级规格)", required = true)
    @NotNull(message = "规格等级不能为空")
    private SpecLevel level;

    @Data
    public static class SpecValue {

        @ApiModelProperty(value = "规格值id(编辑时不能为空)")
        private Long id;

        @ApiModelProperty(value = "规格值", required = true)
        @Size(min = 1, max = 20, message = "规格值长度1~20位")
        @NotBlank(message = "规格值不能为空")
        @WordChecker(message = "规格值存在敏感词")
        private String name;

        @ApiModelProperty(value = "规格图片(一级规格必填), 优先级比sku_pic低", required = true)
        private String pic;
    }
}
