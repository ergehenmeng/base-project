package com.eghm.vo.business.order.ticket;

import com.eghm.convertor.CentToYuanSerializer;
import com.eghm.convertor.SplitterIndexSerializer;
import com.eghm.enums.PayType;
import com.eghm.enums.RefundState;
import com.eghm.enums.RefundType;
import com.eghm.vo.business.order.OrderVisitorVerifiableVO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @since 2023/7/31
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TicketOrderDetailVO extends OrderVisitorVerifiableVO {

    @ApiModelProperty("图片")
    @JsonSerialize(using = SplitterIndexSerializer.class)
    private String coverUrl;

    @ApiModelProperty("门票名称")
    private String title;

    @ApiModelProperty(value = "门票种类 1:成人 2:老人 3:儿童  4:演出 5:活动 6:研学 7:套票")
    private Integer category;

    @ApiModelProperty("景区名称")
    private String scenicName;

    @ApiModelProperty("景区名称")
    private Long scenicId;

    @ApiModelProperty("支付方式(支付成功才会有支付方式)")
    private PayType payType;

    @ApiModelProperty("购买数量")
    private Integer num;

    @ApiModelProperty(value = "是否支持退款 0:不支持 1:直接退款 2:审核后退款")
    private RefundType refundType;

    @ApiModelProperty("当前订单所处的退款状态 1:退款申请中 2:退款中 3:退款拒绝 4:退款成功 5:退款失败(该状态和退款中在C端用户看来都是退款中) 6:线下退款(该状态与退款成功在C端用户看来是一样的)")
    private RefundState refundState;

    @ApiModelProperty(value = "单价")
    @JsonSerialize(using = CentToYuanSerializer.class)
    private Integer price;

    @ApiModelProperty(value = "优惠金额")
    @JsonSerialize(using = CentToYuanSerializer.class)
    private Integer discountAmount;

    @ApiModelProperty("总付款金额")
    @JsonSerialize(using = CentToYuanSerializer.class)
    private Integer payAmount;

    @ApiModelProperty("创建订单时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty("支付时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime payTime;

    @ApiModelProperty(value = "联系人手机号")
    private String mobile;

    @ApiModelProperty("完成时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime completeTime;

    @ApiModelProperty("预计游玩日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate visitDate;

    @ApiModelProperty("订单备注信息")
    private String remark;
}
