package com.eghm.service.business.handler.context;

import com.eghm.annotation.Padding;
import com.eghm.dto.ext.AsyncKey;
import com.eghm.service.business.handler.dto.VisitorDTO;
import com.eghm.state.machine.Context;
import com.eghm.validation.annotation.Mobile;
import com.eghm.validation.annotation.RangeInt;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

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
public class HomestayOrderCreateContext extends AsyncKey implements Context {

    @Padding
    @ApiModelProperty(hidden = true, value = "用户id")
    private Long memberId;

    @ApiModelProperty("商品id")
    @NotNull(message = "商品不能为空")
    private Long roomId;

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
    private List<VisitorDTO> visitorList;

    @ApiModelProperty("开始日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "开始日期不能为空")
    private LocalDate startDate;

    @ApiModelProperty("截止日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "截止日期不能为空")
    private LocalDate endDate;

    @ApiModelProperty("订单编号")
    private String orderNo;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("源状态")
    private Integer from;

}
