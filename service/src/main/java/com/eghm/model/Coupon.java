package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.convertor.CentToYuanSerializer;
import com.eghm.enums.ref.CouponMode;
import com.eghm.enums.ref.CouponType;
import com.eghm.enums.ref.ProductType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "优惠券名称")
    private String title;

    @Schema(description = "优惠券所属商户id")
    private Long merchantId;

    @Schema(description = "状态 0:未启用 1:已启用 ")
    private Integer state;

    @Schema(description = "库存(发放数量)")
    private Integer stock;

    @Schema(description = "已领取数量")
    private Integer receiveNum;

    @Schema(description = "已使用数量")
    private Integer useNum;

    @Schema(description = "单人领取限制")
    private Integer maxLimit;

    @Schema(description = "领取方式  1:页面领取 2:手动发放")
    private CouponMode mode;

    @Schema(description = "优惠券类型 1:抵扣券 2:折扣券")
    private CouponType couponType;

    @Schema(description = "抵扣金额 单位:分")
    @JsonSerialize(using = CentToYuanSerializer.class)
    private Integer deductionValue;

    @Schema(description = "折扣比例 1-100")
    private Integer discountValue;

    @Schema(description = "使用范围  1:店铺通用 2:指定商品(某一个店铺下的商品)")
    private Integer useScope;

    @Schema(description = "商品类型 ticket:门票 homestay:民宿 voucher:餐饮券 item:零售 line:线路 venue:场馆")
    private ProductType productType;

    @Schema(description = "店铺id")
    private Long storeId;

    @Schema(description = "使用门槛 0:不限制 大于0表示限制启用金额 单位:分")
    @JsonSerialize(using = CentToYuanSerializer.class)
    private Integer useThreshold;

    @Schema(description = "发放开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime startTime;

    @Schema(description = "发放截止时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime endTime;

    @Schema(description = "可以使用的开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime useStartTime;

    @Schema(description = "可以使用的截止时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime useEndTime;

    @Schema(description = "使用说明")
    private String instruction;

}
