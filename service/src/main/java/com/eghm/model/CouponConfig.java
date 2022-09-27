package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.common.enums.ref.CouponMode;
import com.eghm.common.enums.ref.CouponType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
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
@EqualsAndHashCode(callSuper = false)
@TableName("coupon_config")
@ApiModel(value="CouponConfig对象", description="优惠券配置表")
public class CouponConfig extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "优惠券名称")
    private String title;

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

    @ApiModelProperty(value = "领取方式 1:页面领取 2: 手动发放")
    private CouponMode mode;

    @ApiModelProperty(value = "优惠券类型 1:抵扣券 2:折扣券")
    private CouponType couponType;

    @ApiModelProperty(value = "折扣比例 1-100")
    private Integer discountValue;

    @ApiModelProperty(value = "抵扣金额 单位:分")
    private Integer deductionValue;

    @ApiModelProperty(value = "使用门槛 0:不限制 大于0表示限制启用金额 单位:分")
    private Integer useThreshold;

    @ApiModelProperty(value = "发放开始时间")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "发放截止时间")
    private LocalDateTime endTime;

    @ApiModelProperty(value = "可以使用的开始时间")
    private LocalDateTime useStartTime;

    @ApiModelProperty(value = "可以使用的截止时间")
    private LocalDateTime useEndTime;

    @ApiModelProperty(value = "使用说明")
    private String instruction;

}
