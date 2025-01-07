package com.eghm.vo.business.order.venue;

import com.eghm.convertor.CentToYuanEncoder;
import com.eghm.enums.ref.*;
import com.eghm.vo.business.venue.VenuePhaseVO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2024/2/5
 */

@Data
public class VenueOrderDetailResponse {

    @Schema(description = "订单号")
    private String orderNo;

    @Schema(description = "场馆名称")
    private String title;

    @Schema(description = "场地名称")
    private String siteTitle;

    @Schema(description = "场馆类型 (1:篮球馆 2:网球馆 3:羽毛球馆 4:乒乓球馆 5:游泳馆 6:健身馆 7:瑜伽馆 8:保龄馆 9:足球馆 10:排球馆 11:田径馆 12:综合馆 13:跆拳道馆)")
    private VenueType venueType;

    @Schema(description = "支付方式")
    private PayType payType;

    @Schema(description = "场馆封面图")
    private String coverUrl;

    @Schema(description = "订单状态")
    private OrderState state;

    @Schema(description = "当前订单所处的退款状态 1:退款申请中 2:退款中 3:退款拒绝 4:退款成功 5:退款失败(该状态和退款中在C端用户看来都是退款中) 6:线下退款(该状态与退款成功在C端用户看来是一样的)")
    private RefundState refundState;

    @Schema(description = "单价")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer price;

    @Schema(description = "总优惠金额(优惠券)")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer discountAmount;

    @Schema(description = "兑换码(只支持线路/民宿/场馆/餐饮)")
    private String cdKey;

    @Schema(description = "兑换码优惠金额")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer cdKeyAmount;

    @Schema(description = "总付款金额=单价*数量-总优惠金额-兑换码优惠")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer payAmount;

    @Schema(description = "预约列表")
    private List<VenuePhaseVO> phaseList;

    @Schema(description = "预约的时间段及价格(json)")
    @JsonIgnore
    private String timePhase;

    @Schema(description = "订单联系人")
    private String nickName;

    @Schema(description = "已退款金额")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer refundAmount;

    @Schema(description = "联系人手机号")
    private String mobile;

    @Schema(description = "预约日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate visitDate;

    @Schema(description = "核销码")
    private String verifyNo;

    @Schema(description = "备注信息")
    private String remark;

    @Schema(description = "支付时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime payTime;

    @Schema(description = "完成时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime completeTime;

    @Schema(description = "订单关闭时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime closeTime;

    @Schema(description = "下单时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @Schema(description = "关闭类型 1:过期自动关闭 2:用户取消 3: 退款完成")
    private CloseType closeType;
}
