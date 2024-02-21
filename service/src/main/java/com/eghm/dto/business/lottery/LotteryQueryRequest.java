package com.eghm.dto.business.lottery;

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
public class LotteryQueryRequest extends PagingQuery {

    @ApiModelProperty("0:未开始 1:进行中 2:已结束")
    private Integer state;

    @ApiModelProperty(value = "所属商户id", hidden = true)
    private Long merchantId;
}
