package com.eghm.state.machine.context;

import com.eghm.annotation.Assign;
import com.eghm.dto.ext.AsyncKey;
import com.eghm.state.machine.Context;
import com.eghm.state.machine.dto.VisitorDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "门票id")
    private Long ticketId;

    @Schema(description = "手机号码")
    private String mobile;

    @Schema(description = "游玩日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate visitDate;

    @Schema(description = "购票数量")
    private Integer num;

    @Schema(description = "游客信息")
    private List<VisitorDTO> visitorList;

    @Schema(description = "优惠券id")
    private Long couponId;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "订单编号")
    @Assign
    private String orderNo;

    @Assign
    @Schema(description = "用户id")
    private Long memberId;

    @Schema(description = "源状态")
    private Integer from;
}
