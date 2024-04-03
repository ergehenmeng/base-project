package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.convertor.CentToYuanEncoder;
import com.eghm.enums.ref.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty("商户id")
    private Long merchantId;

    @ApiModelProperty(value = "商品名称")
    private String title;

    @ApiModelProperty(value = "封面图片(第一张)")
    private String coverUrl;

    @ApiModelProperty("订单编号")
    private String orderNo;

    @ApiModelProperty("用户id")
    private Long memberId;

    @ApiModelProperty("商品所属店铺")
    private Long storeId;

    /**
     * 零售由于可能是多商品同时下单, 该字段没有意义, 因此会为空
     */
    @ApiModelProperty(value = "单价")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer price;

    @ApiModelProperty(value = "数量")
    private Integer num;

    @ApiModelProperty(value = "商品类型 ticket:门票 homestay:民宿 restaurant:餐饮券 item:零售 line:线路 venue:场馆")
    private ProductType productType;

    @ApiModelProperty("是否为多订单,普通商品且购物车购买才可能是多订单,即一个订单对应多商品")
    private Boolean multiple;

    @ApiModelProperty(value = "退款方式 0:不支持 1:直接退款 2:审核后退款")
    private RefundType refundType;

    @ApiModelProperty("退款描述信息")
    private String refundDescribe;

    @ApiModelProperty("支付流水号")
    private String tradeNo;

    @ApiModelProperty("支付方式")
    private PayType payType;

    @ApiModelProperty(value = "订单状态")
    private OrderState state;

    @ApiModelProperty("当前订单所处的退款状态 1:退款申请中 2: 退款中 3: 退款拒绝 4: 退款成功 5: 退款失败(该状态和退款中在C端用户看来都是退款中) 6: 线下退款(该状态与退款成功在C端用户看来是一样的)")
    private RefundState refundState;

    @ApiModelProperty("关闭类型 (1:订单自动过期 2:用户取消 3:退款成功)")
    private CloseType closeType;

    @ApiModelProperty("是否已评价")
    private Boolean evaluateState;

    @ApiModelProperty(value = "是否已结算")
    private Boolean settleState;

    @ApiModelProperty(value = "总优惠金额")
    private Integer discountAmount;

    @ApiModelProperty(value = "总付款金额=单价*数量+总快递费-总优惠金额-兑换码-积分")
    private Integer payAmount;

    @ApiModelProperty("总快递费")
    private Integer expressAmount;

    @ApiModelProperty("已退款金额")
    private Integer refundAmount;

    @ApiModelProperty("已退款积分")
    private Integer refundScoreAmount;

    @ApiModelProperty(value = "优惠券id")
    private Long couponId;

    @ApiModelProperty("支付时间")
    private LocalDateTime payTime;

    @ApiModelProperty("完成时间")
    private LocalDateTime completeTime;

    @ApiModelProperty("订单关闭时间")
    private LocalDateTime closeTime;

    @ApiModelProperty("收货人姓名")
    private String nickName;

    @ApiModelProperty("收货人手机号")
    private String mobile;

    @ApiModelProperty(value = "省份id")
    private Long provinceId;

    @ApiModelProperty(value = "城市id")
    private Long cityId;

    @ApiModelProperty(value = "县区id")
    private Long countyId;

    @ApiModelProperty(value = "详细地址")
    private String detailAddress;

    @ApiModelProperty("核销码")
    private String verifyNo;

    @ApiModelProperty("订单备注信息")
    private String remark;

    @ApiModelProperty("限时购活动id")
    private Long limitId;

    @ApiModelProperty("拼团活动id")
    private Long bookingId;

    @ApiModelProperty("拼团单号")
    private String bookingNo;

    /**
     * 1. 只有拼团成功的商品才能进行发货或核销
     * 2. 退款审核通过后即为拼团失败
     */
    @ApiModelProperty("拼团状态 0:待成团 1:拼团成功 2:拼团失败")
    private Integer bookingState;

    @ApiModelProperty("创建日期")
    private LocalDate createDate;

    @ApiModelProperty("兑换码")
    private String cdKey;

    @ApiModelProperty("兑换码金额")
    private Integer cdKeyAmount;

    @ApiModelProperty("使用的积分")
    private Integer scoreAmount;
}
