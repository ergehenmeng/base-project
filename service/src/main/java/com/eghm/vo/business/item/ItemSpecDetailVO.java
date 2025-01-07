package com.eghm.vo.business.item;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2023/8/30
 */
@Data
public class ItemSpecDetailVO {

    @Schema(description = "id")
    private Long id;

    @Schema(description = "规格名称")
    private String specValue;

    @Schema(description = "规格图片(一级规格必填), 优先级比sku_pic低")
    private String specPic;
}
