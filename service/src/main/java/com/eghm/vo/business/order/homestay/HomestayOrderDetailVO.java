package com.eghm.vo.business.order.homestay;

import com.eghm.convertor.CentToYuanEncoder;
import com.eghm.enums.ref.*;
import com.eghm.vo.business.order.VisitorVO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2023/7/31
 */
@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class HomestayOrderDetailVO {

    @ApiModelProperty("图片")
    private String coverUrl;

    @ApiModelProperty("订单编号")
    private String orderNo;

    @ApiModelProperty("房型名称")
    private String title;

    @ApiModelProperty("民宿名称")
    private String homestayName;

    @ApiModelProperty("民宿id")
    private Long homestayId;

    @ApiModelProperty("支付方式(支付成功才会有支付方式)")
    private PayType payType;

    @ApiModelProperty("购买数量")
    private Integer num;

    @ApiModelProperty(value = "是否支持退款 0:不支持 1:直接退款 2:审核后退款")
    private RefundType refundType;

    @ApiModelProperty(value = "订单状态")
    private OrderState state;

    @ApiModelProperty("当前订单所处的退款状态 1:退款申请中 2: 退款中 3: 退款拒绝 4: 退款成功 5: 退款失败(该状态和退款中在C端用户看来都是退款中) 6: 线下退款(该状态与退款成功在C端用户看来是一样的)")
    private RefundState refundState;

    @ApiModelProperty(value = "单价")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer price;

    @ApiModelProperty(value = "优惠金额")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer discountAmount;

    @ApiModelProperty("总付款金额")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer payAmount;

    @ApiModelProperty("创建订单时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty("支付时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime payTime;

    @ApiModelProperty(value = "联系人手机号")
    private String mobile;

    @ApiModelProperty("游客列表")
    private List<VisitorVO> visitorList;

    @ApiModelProperty("确认状态 0:待确认 1:确认有房 2:确认无房 3:自动确认有房")
    private ConfirmState confirmState;

    @ApiModelProperty("确认无房备注信息")
    private String confirmRemark;

    @ApiModelProperty("完成时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime completeTime;

    @ApiModelProperty("入住日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @ApiModelProperty("离店日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @ApiModelProperty("核销码")
    private String verifyNo;

    @ApiModelProperty("订单备注信息")
    private String remark;
}
