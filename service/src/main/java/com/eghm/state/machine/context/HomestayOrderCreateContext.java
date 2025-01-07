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
public class HomestayOrderCreateContext extends AsyncKey implements Context {

    @Schema(description = "商品id")
    private Long roomId;

    @Schema(description = "优惠券id")
    private Long couponId;

    @Schema(description = "联系人姓名")
    private String nickName;

    @Schema(description = "联系人电话")
    private String mobile;

    @Schema(description = "房间数")
    private Integer num;

    @Schema(description = "入住人信息列表")
    private List<VisitorDTO> visitorList;

    @Schema(description = "开始日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate startDate;

    @Schema(description = "截止日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate endDate;

    @Schema(description = "兑换码")
    private String cdKey;

    @Schema(description = "备注")
    private String remark;

    @Assign
    @Schema(description = "订单编号")
    private String orderNo;

    @Assign
    @Schema(description = "用户id", hidden = true)
    private Long memberId;

    @Schema(description = "源状态")
    private Integer from;
}
