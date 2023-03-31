package com.eghm.service.business.handler.dto;

import com.eghm.common.annotation.Sign;
import com.eghm.model.dto.ext.AsyncKey;
import com.eghm.model.validation.annotation.Mobile;
import com.eghm.model.validation.annotation.RangeInt;
import com.eghm.state.machine.Context;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2022/11/22
 */
@Getter
@Setter
public class TicketOrderCreateContext extends AsyncKey implements Context {

    @ApiModelProperty("商品id")
    @NotNull(message = "商品不能为空")
    private Long ticketId;

    @RangeInt(min = 1, max = 9, message = "购买数量应为1~9张")
    @ApiModelProperty("门票数量")
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

    @ApiModelProperty("入园信息列表")
    @Size(min = 1, max = 9, message = "入园人数不能超过9人")
    private List<VisitorVO> visitorList;

    @Sign
    @ApiModelProperty(hidden = true, value = "用户id")
    private Long userId;
    @ApiModelProperty("源状态")
    private Integer from;

    @ApiModelProperty("新状态")
    private Integer to;

    @Override
    public void setFrom(Integer from) {
        this.from = from;
    }

    @Override
    public void setTo(Integer to) {
        this.to = to;
    }

    @Override
    public Integer getFrom() {
        return from;
    }

    @Override
    public Integer getTo() {
        return to;
    }
}
