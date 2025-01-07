package com.eghm.vo.business.item;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2023/8/30
 */
@Data
public class ItemSpecVO {

    @Schema(description = "规格名")
    private String specName;

    @Schema(description = "规格值列表")
    private List<ItemSpecDetailVO> valueList;
}
