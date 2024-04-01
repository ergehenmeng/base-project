package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.convertor.CentToYuanEncoder;
import com.eghm.enums.ref.CouponMode;
import com.eghm.enums.ref.CouponType;
import com.eghm.enums.ref.ProductType;
import com.eghm.handler.mysql.LikeTypeHandler;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * <p>
 * 优惠券配置表
 * </p>
 *
 * @author 二哥很猛
 * @since 2022-07-13
 */
@Data
@TableName("coupon")
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Coupon extends BaseEntity {

    @ApiModelProperty(value = "优惠券名称")
    @TableField(typeHandler = LikeTypeHandler.class)
    private String title;

    @ApiModelProperty("优惠券所属商户id")
    private Long merchantId;

    @ApiModelProperty("状态 0:未启用 1:已启用 ")
    private Integer state;

    @ApiModelProperty(value = "库存(发放数量)")
    private Integer stock;

    @ApiModelProperty(value = "已领取数量")
    private Integer receiveNum;

    @ApiModelProperty(value = "已使用数量")
    private Integer useNum;

    @ApiModelProperty(value = "单人领取限制")
    private Integer maxLimit;

    @ApiModelProperty(value = "领取方式  1:页面领取 2:手动发放")
    private CouponMode mode;

    @ApiModelProperty(value = "优惠券类型 1:抵扣券 2:折扣券")
    private CouponType couponType;

    @ApiModelProperty(value = "抵扣金额 单位:分")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer deductionValue;

    @ApiModelProperty(value = "折扣比例 1-100")
    private Integer discountValue;

    @ApiModelProperty("使用范围  1:店铺通用 2:指定商品(某一个店铺下的商品)")
    private Integer useScope;

    @ApiModelProperty(value = "商品类型 ticket:门票 homestay:民宿 voucher:餐饮券 item:零售 line:线路 venue:场馆")
    private ProductType productType;

    @ApiModelProperty("店铺id")
    private Long storeId;

    @ApiModelProperty(value = "使用门槛 0:不限制 大于0表示限制启用金额 单位:分")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer useThreshold;

    @ApiModelProperty(value = "发放开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "发放截止时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime endTime;

    @ApiModelProperty(value = "可以使用的开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime useStartTime;

    @ApiModelProperty(value = "可以使用的截止时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime useEndTime;

    @ApiModelProperty(value = "使用说明")
    private String instruction;

}
