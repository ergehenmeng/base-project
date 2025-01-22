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
public class HomestayOrderCreateContext extends BaseAsyncKey implements Context {

    @ApiModelProperty("商品id")
    private Long roomId;

    @ApiModelProperty("优惠券id")
    private Long couponId;

    @ApiModelProperty("联系人姓名")
    private String nickName;

    @ApiModelProperty("联系人电话")
    private String mobile;

    @ApiModelProperty("房间数")
    private Integer num;

    @ApiModelProperty("入住人信息列表")
    private List<VisitorDTO> visitorList;

    @ApiModelProperty("开始日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate startDate;

    @ApiModelProperty("截止日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate endDate;

    @ApiModelProperty("兑换码")
    private String cdKey;

    @ApiModelProperty("备注")
    private String remark;

    @Assign
    @ApiModelProperty("订单编号")
    private String orderNo;

    @Assign
    @ApiModelProperty(hidden = true, value = "用户id")
    private Long memberId;

    @ApiModelProperty("源状态")
    private Integer from;
}
