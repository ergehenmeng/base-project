package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.convertor.CentToYuanEncoder;
import com.eghm.enums.ref.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 订单表
 * </p>
 *
 * @author 二哥很猛
 * @since 2022-08-17
 */
@Data
@TableName("`order`")
@EqualsAndHashCode(callSuper = true)
public class Order extends BaseEntity {

    @Schema(description = "商户id")
    private Long merchantId;

    @Schema(description = "商品名称")
    private String title;

    @Schema(description = "封面图片(第一张)")
    private String coverUrl;

    @Schema(description = "订单编号")
    private String orderNo;

    @Schema(description = "用户id")
    private Long memberId;

    @Schema(description = "商品所属店铺")
    private Long storeId;

    /**
     * 零售由于可能是多商品同时下单, 该字段没有意义, 因此会为空
     */
    @Schema(description = "单价")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer price;

    @Schema(description = "数量")
    private Integer num;

    @Schema(description = "商品类型 ticket:门票 homestay:民宿 voucher:餐饮券 item:零售 line:线路 venue:场馆")
    private ProductType productType;

    @Schema(description = "是否为多订单,普通商品且购物车购买才可能是多订单,即一个订单对应多商品")
    private Boolean multiple;

    @Schema(description = "退款方式 0:不支持 1:直接退款 2:审核后退款")
    private RefundType refundType;

    @Schema(description = "退款描述信息")
    private String refundDescribe;

    @Schema(description = "支付流水号")
    private String tradeNo;

    @Schema(description = "支付方式")
    private PayType payType;

    @Schema(description = "订单状态")
    private OrderState state;

    @Schema(description = "当前订单所处的退款状态 1:退款申请中 2:退款中 3:退款拒绝 4:退款成功 5:退款失败(该状态和退款中在C端用户看来都是退款中) 6:线下退款(该状态与退款成功在C端用户看来是一样的)")
    private RefundState refundState;

    @Schema(description = "关闭类型 (1:订单自动过期 2:用户取消 3:退款成功)")
    private CloseType closeType;

    @Schema(description = "是否已评价")
    private Boolean evaluateState;

    @Schema(description = "是否已结算")
    private Boolean settleState;

    @Schema(description = "订单金额=单价*数量")
    private Integer amount;

    @Schema(description = "总优惠金额(优惠券优惠)")
    private Integer discountAmount;

    @Schema(description = "总付款金额=单价*数量+总快递费(如果有的话)-总优惠金额-兑换码(如果有的话)-积分(如果有的话)")
    private Integer payAmount;

    @Schema(description = "总快递费")
    private Integer expressAmount;

    @Schema(description = "已退款金额")
    private Integer refundAmount;

    @Schema(description = "已退款积分")
    private Integer refundScoreAmount;

    @Schema(description = "优惠券id")
    private Long couponId;

    @Schema(description = "支付时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime payTime;

    @Schema(description = "完成时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime completeTime;

    @Schema(description = "订单关闭时间")
    private LocalDateTime closeTime;

    @Schema(description = "收货人姓名")
    private String nickName;

    @Schema(description = "收货人手机号")
    private String mobile;

    @Schema(description = "省份id")
    private Long provinceId;

    @Schema(description = "城市id")
    private Long cityId;

    @Schema(description = "县区id")
    private Long countyId;

    @Schema(description = "详细地址")
    private String detailAddress;

    @Schema(description = "核销码")
    private String verifyNo;

    @Schema(description = "订单备注信息")
    private String remark;

    @Schema(description = "限时购活动id")
    private Long limitId;

    @Schema(description = "拼团活动id")
    private Long bookingId;

    @Schema(description = "拼团单号")
    private String bookingNo;

    /**
     * 1. 只有拼团成功的商品才能进行发货或核销
     * 2. 退款审核通过后即为拼团失败
     */
    @Schema(description = "拼团状态 0:待成团 1:拼团成功 2:拼团失败")
    private Integer bookingState;

    @Schema(description = "创建日期")
    private LocalDate createDate;

    @Schema(description = "创建月份")
    private String createMonth;

    @Schema(description = "兑换码(只支持线路/民宿/场馆/餐饮)")
    private String cdKey;

    @Schema(description = "兑换码优惠金额")
    private Integer cdKeyAmount;

    @Schema(description = "使用的积分(只支持零售)")
    private Integer scoreAmount;
}
