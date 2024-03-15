package com.eghm.service.business.handler.context;

import com.eghm.annotation.Assign;
import com.eghm.dto.ext.AsyncKey;
import com.eghm.service.business.handler.dto.VisitorDTO;
import com.eghm.state.machine.Context;
import com.eghm.validation.annotation.Mobile;
import com.eghm.validation.annotation.RangeInt;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2022/11/22
 */
@Getter
@Setter
@ToString(callSuper = true)
public class LineOrderCreateContext extends AsyncKey implements Context {

    @ApiModelProperty("商品id")
    @NotNull(message = "商品不能为空")
    private Long lineId;

    @RangeInt(min = 1, max = 99, message = "购买数量应为1~99张")
    @ApiModelProperty("数量")
    private Integer num;

    @ApiModelProperty("优惠券id")
    private Long couponId;

    @ApiModelProperty("联系人电话")
    @Mobile(message = "联系人手机号格式错误")
    private String mobile;

    @ApiModelProperty("联系人姓名")
    @Size(min = 2, max = 10, message = "联系人姓名应为2~10字符")
    @NotBlank(message = "联系人姓名不能为空")
    private String nickName;

    @ApiModelProperty("线路人数列表")
    @Size(min = 1, max = 99, message = "人数不能超过99人")
    private List<VisitorDTO> visitorList;

    @ApiModelProperty("游玩日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "游玩日期不能为空")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate configDate;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("兑换码")
    private String cdKey;

    @ApiModelProperty("订单编号")
    @Assign
    private String orderNo;

    @Assign
    @ApiModelProperty(hidden = true, value = "用户id")
    private Long memberId;

    @ApiModelProperty("源状态")
    private Integer from;
}
