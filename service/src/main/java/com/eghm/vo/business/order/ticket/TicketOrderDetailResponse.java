package com.eghm.vo.business.order.ticket;

import com.eghm.convertor.CentToYuanSerializer;
import com.eghm.enums.CloseType;
import com.eghm.enums.OrderState;
import com.eghm.enums.PayType;
import com.eghm.enums.RefundState;
import com.eghm.vo.business.order.VisitorVO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author wyb
 * @since 2023/6/8
 */
@Data
public class TicketOrderDetailResponse {

    @ApiModelProperty("订单编号")
    private String orderNo;

    @ApiModelProperty("门票名称")
    private String title;

    @ApiModelProperty(value = "封面图片")
    private String coverUrl;

    @ApiModelProperty("景区名称")
    private String scenicName;

    @ApiModelProperty("支付方式")
    private PayType payType;

    @ApiModelProperty("支付流水号")
    private String tradeNo;

    @ApiModelProperty("购买数量")
    private Integer num;

    @ApiModelProperty("单价")
    @JsonSerialize(using = CentToYuanSerializer.class)
    private Integer price;

    @ApiModelProperty("订单联系人")
    private String mobile;

    @ApiModelProperty("预计游玩日期")
    private LocalDate visitDate;

    @ApiModelProperty(value = "订单状态 0:待支付 1:支付中 2:待使用 3:待自提 4:待发货 5:部分发货 6:待收货 7:退款中 8:订单完成 9:已关闭 10:支付异常 11:退款异常")
    private OrderState state;

    @ApiModelProperty(value = "门票种类 1:成人 2:老人 3:儿童  4:演出 5:活动 6:研学 7:组合")
    private Integer category;

    @ApiModelProperty("当前订单所处的退款状态 1:退款申请中 2:退款中 3:退款拒绝 4:退款成功 5:退款失败(该状态和退款中在C端用户看来都是退款中) 6:线下退款(该状态与退款成功在C端用户看来是一样的)")
    private RefundState refundState;

    @ApiModelProperty("关闭类型 1:过期自动关闭 2:用户取消 3:退款完成")
    private CloseType closeType;

    @ApiModelProperty("付款金额")
    @JsonSerialize(using = CentToYuanSerializer.class)
    private Integer payAmount;

    @ApiModelProperty(value = "总优惠金额")
    @JsonSerialize(using = CentToYuanSerializer.class)
    private Integer discountAmount;

    @ApiModelProperty("门票核销时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime useTime;

    @ApiModelProperty("完成时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime completeTime;

    @ApiModelProperty("订单关闭时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime closeTime;

    @ApiModelProperty("支付时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime payTime;

    @ApiModelProperty("订单创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "是否实名购票(实名制显示列表) false:不实名 true:实名")
    private Boolean realBuy;

    @ApiModelProperty("购票人列表")
    private List<VisitorVO> visitorList;

    @ApiModelProperty("订单备注信息")
    private String remark;
}
