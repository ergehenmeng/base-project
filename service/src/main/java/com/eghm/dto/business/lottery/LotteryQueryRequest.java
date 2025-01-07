package com.eghm.dto.business.lottery;

import com.eghm.annotation.Assign;
import com.eghm.dto.ext.PagingQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 二哥很猛
 * @since 2023/8/2
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class LotteryQueryRequest extends PagingQuery {

    @Schema(description = "0:未开始 1:进行中 2:已结束")
    private Integer state;

    @Assign
    @Schema(description = "所属商户id", hidden = true)
    private Long merchantId;
}
