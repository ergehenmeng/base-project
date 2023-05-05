package com.eghm.service.business.handler.context;

import com.eghm.annotation.Sign;
import com.eghm.dto.ext.AsyncKey;
import com.eghm.service.business.handler.dto.VisitorDTO;
import com.eghm.state.machine.Context;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2022/11/22
 */
@Getter
@Setter
public class TicketOrderCreateContext extends AsyncKey implements Context {

    @Sign
    @ApiModelProperty(hidden = true, value = "用户id")
    private Long userId;

    @ApiModelProperty("门票id")
    private Long ticketId;

    @ApiModelProperty("手机号码")
    private String mobile;

    @ApiModelProperty("游玩日期")
    private LocalDate visitDate;

    @ApiModelProperty("游客信息")
    private List<VisitorDTO> visitorList;

    @ApiModelProperty("优惠券id")
    private Long couponId;

    @ApiModelProperty("订单编号")
    private String orderNo;

    @ApiModelProperty("源状态")
    private Integer from;

    @ApiModelProperty("新状态")
    private Integer to;

}
