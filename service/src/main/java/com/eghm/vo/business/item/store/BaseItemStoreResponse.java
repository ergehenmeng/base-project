package com.eghm.vo.business.item.store;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2024/6/10
 */

@Data
public class BaseItemStoreResponse {

    @Schema(description = "店铺id")
    private Long id;

    @Schema(description = "店铺名称")
    private String title;

    @Schema(description = "上下架状态 0:待上架 1:已上架")
    private Integer state;
}
