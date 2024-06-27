package com.eghm.vo.business.coupon;

import com.eghm.convertor.CentToYuanEncoder;
import com.eghm.convertor.CentToYuanOmitEncoder;
import com.eghm.enums.ref.CouponMode;
import com.eghm.enums.ref.CouponType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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
public class CouponResponse {

    @ApiModelProperty(value = "优惠券id")
    private Long id;

    @ApiModelProperty(value = "优惠券名称")
    private String title;

    @ApiModelProperty("状态 0:未启用 1:已启用")
    private Integer state;

    @ApiModelProperty(value = "库存(总数量)")
    private Integer stock;

    @ApiModelProperty(value = "已领取数量")
    private Integer receiveNum;

    @ApiModelProperty(value = "已使用数量")
    private Integer useNum;

    @ApiModelProperty(value = "领取方式 (1:页面领取 2:手动发放)")
    private CouponMode mode;

    @ApiModelProperty(value = "优惠券类型 (1:抵扣券 2:折扣券)")
    private CouponType couponType;

    @ApiModelProperty(value = "抵扣金额 单位:分")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer deductionValue;

    @ApiModelProperty(value = "折扣比例 1-100")
    private Integer discountValue;

    @ApiModelProperty(value = "使用门槛 0:不限制 大于0表示限制启用金额 单位:分")
    @JsonSerialize(using = CentToYuanOmitEncoder.class)
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

}
