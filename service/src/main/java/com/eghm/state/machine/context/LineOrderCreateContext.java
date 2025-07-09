package com.eghm.state.machine.context;

import com.eghm.annotation.Assign;
import com.eghm.dto.ext.BaseAsyncKey;
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
public class LineOrderCreateContext extends BaseAsyncKey implements Context {

    @Schema(description = "商品id")
    private Long lineId;

    @Schema(description = "数量")
    private Integer num;

    @Schema(description = "会员优惠券id")
    private Long memberCouponId;

    @Schema(description = "联系人电话")
    private String mobile;

    @Schema(description = "联系人姓名")
    private String nickName;

    @Schema(description = "线路人数列表")
    private List<VisitorDTO> visitorList;

    @Schema(description = "游玩日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate configDate;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "兑换码")
    private String cdKey;

    @Schema(description = "订单编号")
    @Assign(Assign.Type.UP)
    private String orderNo;

    @Schema(description = "支付金额")
    @Assign(Assign.Type.UP)
    private Integer payAmount;

    @Assign
    @Schema(description = "用户id")
    private Long memberId;

    @Schema(description = "源状态")
    private Integer from;
}
