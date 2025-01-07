package com.eghm.dto.business.item.store;

import com.eghm.dto.ext.PagingQuery;
import com.eghm.enums.ref.State;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 二哥很猛
 * @since 2022/7/1
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ItemStoreQueryRequest extends PagingQuery {

    @Schema(description = "状态 0:待上架 1:已上架 2:强制下架")
    private State state;

    @Schema(description = "商户id")
    private Long merchantId;
}
