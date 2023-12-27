package com.eghm.dto.business.coupon.config;

import com.eghm.convertor.YuanToCentDecoder;
import com.eghm.enums.ref.CouponType;
import com.eghm.validation.annotation.OptionInt;
import com.eghm.validation.annotation.RangeInt;
import com.eghm.validation.annotation.WordChecker;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 二哥很猛
 * @date 2022/7/13
 */
@Data
public class CouponAddRequest {

    @ApiModelProperty(value = "优惠券名称", required = true)
    @Size(min = 2, max = 20, message = "优惠券名称长度2~20位")
    @NotBlank(message = "优惠券名称不能为空")
    @WordChecker(message = "优惠券名称存在敏感词")
    private String title;

    @ApiModelProperty(value = "库存(发放数量)", required = true)
    @RangeInt(min = 1, max = 9999, message = "库存应为1~9999")
    private Integer stock;

    @ApiModelProperty(value = "单人领取限制", required = true)
    @RangeInt(min = 1, max = 99, message = "单人领取限制1~99")
    private Integer maxLimit;

    @ApiModelProperty(value = "领取方式 1:页面领取 2: 手动发放", required = true)
    @OptionInt(value = {1, 2}, message = "领取方式非法")
    private Integer mode;

    @ApiModelProperty(value = "优惠券类型", required = true)
    private CouponType couponType;

    @ApiModelProperty(value = "使用范围  1:店铺通用 2:指定商品", required = true)
    private Integer useScope;

    @ApiModelProperty("店铺id")
    private Long storeId;

    @ApiModelProperty(value = "抵扣金额 单位:元")
    @JsonDeserialize(using = YuanToCentDecoder.class)
    private Integer deductionValue;

    @ApiModelProperty(value = "折扣比例 10-100")
    @RangeInt(min = 10, max = 100, required = false, message = "折扣比例应为10~100之间")
    private Integer discountValue;

    @ApiModelProperty(value = "使用门槛 0:不限制 大于0表示限制启用金额 单位:元")
    @JsonDeserialize(using = YuanToCentDecoder.class)
    private Integer useThreshold;

    @ApiModelProperty(value = "发放开始时间 yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "发放截止时间 yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime endTime;

    @ApiModelProperty(value = "可以使用的开始时间 yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime useStartTime;

    @ApiModelProperty(value = "可以使用的截止时间 yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime useEndTime;

    @ApiModelProperty(value = "使用说明")
    @WordChecker(message = "使用说明存在敏感词")
    private String instruction;

    @ApiModelProperty("关联的商品列表")
    private List<Long> itemIds;
}
