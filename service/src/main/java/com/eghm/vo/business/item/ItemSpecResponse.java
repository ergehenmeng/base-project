package com.eghm.vo.business.item;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @author 殿小二
 * @since 2023/3/7
 */

@Data
public class ItemSpecResponse {

    @Schema(description = "规格id")
    private Long specId;

    @Schema(description = "规格名")
    private String specName;

    @Schema(description = "标签级别(1:一级标签 2:二级标签)")
    private Integer level;

    @Schema(description = "规格值列表")
    private List<ItemSpecValueResponse> valueList;

}
