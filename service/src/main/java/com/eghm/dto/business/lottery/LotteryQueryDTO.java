
package com.eghm.dto.business.lottery;

import com.eghm.annotation.Assign;
import com.eghm.dto.ext.PagingQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 二哥很猛
 * @since 2023/8/2
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class LotteryQueryDTO extends PagingQuery {

    @ApiModelProperty("中奖状态 false:未中奖 true:已中奖")
    private Boolean winning;

    @ApiModelProperty("抽奖活动id")
    private Long lotteryId;

    @Assign
    @ApiModelProperty(value = "用户id", hidden = true)
    private Long memberId;
}
