package com.eghm.dto.business.order.homestay;

import com.eghm.annotation.Assign;
import com.eghm.dto.ext.PagingQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 二哥很猛
 * @since 2023/7/28
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class HomestayOrderQueryDTO extends PagingQuery {

    @Schema(description = "民宿订单状态")
    private Integer orderState;

    @Schema(description = "用户id", hidden = true)
    @Assign
    private Long memberId;
}
