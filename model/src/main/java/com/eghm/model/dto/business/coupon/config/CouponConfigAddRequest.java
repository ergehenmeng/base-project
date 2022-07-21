package com.eghm.model.dto.business.coupon.config;

import com.eghm.common.convertor.YuanToCentDecoder;
import com.eghm.common.enums.ref.CouponType;
import com.eghm.model.dto.business.coupon.product.CouponProductRequest;
import com.eghm.model.validation.annotation.OptionInt;
import com.eghm.model.validation.annotation.RangeInt;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 二哥很猛
 * @date 2022/7/13
 */
@Data
public class CouponConfigAddRequest {

    @ApiModelProperty(value = "优惠券名称")
    @Size(min = 2, max = 20, message = "优惠券名称长度2~20位")
    private String title;

    @ApiModelProperty(value = "库存(发放数量)")
    @RangeInt(min = 1, max = 9999, message = "库存应为1~9999")
    private Integer stock;

    @ApiModelProperty(value = "单人领取限制")
    @RangeInt(min = 1, max = 99, message = "单人领取限制1~99")
    private Integer maxLimit;

    @ApiModelProperty(value = "领取方式 1:页面领取 2: 手动发放")
    @OptionInt(value = {1, 2}, message = "领取方式非法")
    private Integer mode;

    @ApiModelProperty(value = "优惠券类型 1:抵扣券 2:折扣券")
    private CouponType couponType;

    @JsonDeserialize(using = YuanToCentDecoder.class)
    @ApiModelProperty(value = "折扣比例 1-100")
    private Integer discountValue;

    @ApiModelProperty(value = "抵扣金额 单位:分")
    private Integer deductionValue;

    @ApiModelProperty(value = "使用门槛 0:不限制 大于0表示限制启用金额 单位:分")
    private Integer useThreshold;

    @ApiModelProperty(value = "发放开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "发放截止时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    @ApiModelProperty(value = "可以使用的开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime useStartTime;

    @ApiModelProperty(value = "可以使用的截止时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime useEndTime;

    @ApiModelProperty(value = "使用说明")
    private String instruction;

    @ApiModelProperty("关联的商品列表")
    private List<CouponProductRequest> productList;
}
