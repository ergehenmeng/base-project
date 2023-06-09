package com.eghm.service.business.handler.context;

import com.eghm.annotation.Padding;
import com.eghm.state.machine.Context;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wyb
 * @since 2023/6/1
 */
@Data
public class RefundCancelContext implements Context {

    @Padding
    @ApiModelProperty("用户id")
    private Long memberId;

    @ApiModelProperty("源状态")
    private Integer from;
}
