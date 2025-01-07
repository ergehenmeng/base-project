package com.eghm.dto.business.collect;

import com.eghm.annotation.Assign;
import com.eghm.dto.ext.PagingQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 二哥很猛
 * @since 2024/1/11
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class CollectQueryDTO extends PagingQuery {

    @Schema(description = "当前登录用户", hidden = true)
    @Assign
    private Long memberId;

    @Schema(description = "1:商品 2:店铺")
    private Integer type = 1;
}
