package com.eghm.vo.business.evaluation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2023/8/29
 */
@Data
public class ApplauseRateVO {

    @ApiModelProperty("评论总数量")
    private Integer sum;

    @ApiModelProperty("好评率百分比")
    private Integer rate;

}
