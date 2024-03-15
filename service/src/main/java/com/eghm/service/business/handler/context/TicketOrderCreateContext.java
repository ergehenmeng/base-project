package com.eghm.service.business.handler.context;

import com.eghm.annotation.Assign;
import com.eghm.dto.ext.AsyncKey;
import com.eghm.service.business.handler.dto.VisitorDTO;
import com.eghm.state.machine.Context;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2022/11/22
 */
@Getter
@Setter
@ToString(callSuper = true)
public class TicketOrderCreateContext extends AsyncKey implements Context {

    @ApiModelProperty("门票id")
    private Long ticketId;

    @ApiModelProperty("手机号码")
    private String mobile;

    @ApiModelProperty("游玩日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate visitDate;

    @ApiModelProperty("购票数量")
    private Integer num;

    @ApiModelProperty("游客信息")
    private List<VisitorDTO> visitorList;

    @ApiModelProperty("优惠券id")
    private Long couponId;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("订单编号")
    @Assign
    private String orderNo;

    @Assign
    @ApiModelProperty(hidden = true, value = "用户id")
    private Long memberId;

    @ApiModelProperty("源状态")
    private Integer from;

}
