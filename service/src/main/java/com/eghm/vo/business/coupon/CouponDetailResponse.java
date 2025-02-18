package com.eghm.vo.business.coupon;

import com.eghm.convertor.CentToYuanSerializer;
import com.eghm.enums.CouponMode;
import com.eghm.enums.CouponType;
import com.eghm.enums.ProductType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 优惠券配置表
 * </p>
 *
 * @author 二哥很猛
 * @since 2022-07-13
 */
@Data
public class CouponDetailResponse {

    @ApiModelProperty("id主键")
    private Long id;

    @ApiModelProperty(value = "优惠券名称")
    private String title;

    @ApiModelProperty(value = "库存(发放数量)")
    private Integer stock;

    @ApiModelProperty(value = "单人领取限制")
    private Integer maxLimit;

    @ApiModelProperty(value = "领取方式  1:页面领取 2:手动发放")
    private CouponMode mode;

    @ApiModelProperty(value = "优惠券类型 1:抵扣券 2:折扣券")
    private CouponType couponType;

    @ApiModelProperty(value = "抵扣金额 单位:分")
    @JsonSerialize(using = CentToYuanSerializer.class)
    private Integer deductionValue;

    @ApiModelProperty(value = "折扣比例 10-99")
    private Integer discountValue;

    @ApiModelProperty("使用范围  1:店铺通用 2:指定商品(某一个店铺下的商品)")
    private Integer useScope;

    @ApiModelProperty(value = "商品类型 ticket:门票 homestay:民宿 voucher:餐饮券 item:零售 line:线路 venue:场馆")
    private ProductType productType;

    @ApiModelProperty("店铺id")
    private Long storeId;

    @ApiModelProperty(value = "使用门槛 0:不限制 大于0表示限制启用金额 单位:分")
    @JsonSerialize(using = CentToYuanSerializer.class)
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

    @ApiModelProperty(value = "商品id列表")
    private List<Long> productIds;

}
