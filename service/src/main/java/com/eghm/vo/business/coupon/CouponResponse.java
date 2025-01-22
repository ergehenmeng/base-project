package com.eghm.vo.business.coupon;

import com.eghm.convertor.CentToYuanSerializer;
import com.eghm.enums.ref.CouponMode;
import com.eghm.enums.ref.CouponType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "优惠券id")
    private Long id;

    @Schema(description = "优惠券名称")
    private String title;

    @Schema(description = "状态 0:未启用 1:已启用")
    private Integer state;

    @Schema(description = "库存(总数量)")
    private Integer stock;

    @Schema(description = "已领取数量")
    private Integer receiveNum;

    @Schema(description = "已使用数量")
    private Integer useNum;

    @Schema(description = "领取方式 (1:页面领取 2:手动发放)")
    private CouponMode mode;

    @Schema(description = "优惠券类型 (1:抵扣券 2:折扣券)")
    private CouponType couponType;

    @Schema(description = "抵扣金额 单位:分")
    @JsonSerialize(using = CentToYuanSerializer.class)
    private Integer deductionValue;

    @Schema(description = "折扣比例 1-100")
    private Integer discountValue;

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

}
