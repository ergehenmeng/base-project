package com.eghm.vo.business.item;

import com.eghm.enums.ref.SpecLevel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 殿小二
 * @since 2023/3/7
 */

@Data
public class ItemSpecValueResponse {

    @Schema(description = "id(编辑时不能为空)")
    private Long id;

    @Schema(description = "规格名")
    @JsonIgnore
    private String specName;

    @Schema(description = "规格值")
    @JsonProperty("name")
    private String specValue;

    @Schema(description = "规格图片(一级规格必填), 优先级比sku_pic低")
    @JsonProperty("pic")
    private String specPic;

    @Schema(description = "标签级别(1:一级标签 2:二级标签)")
    @JsonIgnore
    private SpecLevel level;

}
