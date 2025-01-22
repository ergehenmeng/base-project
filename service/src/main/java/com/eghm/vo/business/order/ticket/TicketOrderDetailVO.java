package com.eghm.vo.business.order.ticket;

import com.eghm.convertor.CentToYuanSerializer;
import com.eghm.enums.OrderState;
import com.eghm.enums.PayType;
import com.eghm.enums.RefundState;
import com.eghm.enums.RefundType;
import com.eghm.vo.business.order.VisitorVO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2023/7/31
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TicketOrderDetailVO {

    @Schema(description = "图片")
    private String coverUrl;

    @Schema(description = "订单编号")
    private String orderNo;

    @Schema(description = "门票名称")
    private String title;

    @Schema(description = "门票种类 1:成人 2:老人 3:儿童  4:演出 5:活动 6:研学 7:组合")
    private Integer category;

    @Schema(description = "景区名称")
    private String scenicName;

    @Schema(description = "景区名称")
    private Long scenicId;

    @Schema(description = "支付方式(支付成功才会有支付方式)")
    private PayType payType;

    @Schema(description = "购买数量")
    private Integer num;

    @Schema(description = "是否支持退款 0:不支持 1:直接退款 2:审核后退款")
    private RefundType refundType;

    @Schema(description = "订单状态 0:待支付 1:支付中 2:待使用 3:待自提 4:待发货 5:部分发货 6:待收货 7:退款中 8:订单完成 9:已关闭 10:支付异常 11:退款异常")
    private OrderState state;

    @Schema(description = "当前订单所处的退款状态 1:退款申请中 2:退款中 3:退款拒绝 4:退款成功 5:退款失败(该状态和退款中在C端用户看来都是退款中) 6:线下退款(该状态与退款成功在C端用户看来是一样的)")
    private RefundState refundState;

    @Schema(description = "单价")
    @JsonSerialize(using = CentToYuanSerializer.class)
    private Integer price;

    @Schema(description = "优惠金额")
    @JsonSerialize(using = CentToYuanSerializer.class)
    private Integer discountAmount;

    @Schema(description = "总付款金额")
    @JsonSerialize(using = CentToYuanSerializer.class)
    private Integer payAmount;

    @Schema(description = "创建订单时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @Schema(description = "支付时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime payTime;

    @Schema(description = "联系人手机号")
    private String mobile;

    @Schema(description = "游客列表")
    private List<VisitorVO> visitorList;

    @Schema(description = "完成时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime completeTime;

    @Schema(description = "预计游玩日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate visitDate;

    @Schema(description = "核销码")
    private String verifyNo;

    @Schema(description = "订单备注信息")
    private String remark;
}
