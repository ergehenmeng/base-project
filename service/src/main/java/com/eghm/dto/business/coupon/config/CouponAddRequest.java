package com.eghm.dto.business.coupon.config;

import com.eghm.annotation.Assign;
import com.eghm.convertor.YuanToCentDeserializer;
import com.eghm.enums.ref.CouponMode;
import com.eghm.enums.ref.CouponType;
import com.eghm.enums.ref.ProductType;
import com.eghm.validation.annotation.OptionInt;
import com.eghm.validation.annotation.RangeInt;
import com.eghm.validation.annotation.WordChecker;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2022/7/13
 */
@Data
public class CouponAddRequest {

    @Schema(description = "优惠券名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @Size(min = 2, max = 20, message = "优惠券名称长度2~20位")
    @NotBlank(message = "优惠券名称不能为空")
    @WordChecker(message = "优惠券名称存在敏感词")
    private String title;

    @Schema(description = "库存(发放数量)", requiredMode = Schema.RequiredMode.REQUIRED)
    @RangeInt(min = 1, max = 9999, message = "库存应为1~9999")
    private Integer stock;

    @Schema(description = "单人领取限制", requiredMode = Schema.RequiredMode.REQUIRED)
    @RangeInt(min = 1, max = 99, message = "单人领取限制1~99")
    private Integer maxLimit;

    @Schema(description = "领取方式  1:页面领取 2:手动发放")
    @NotNull(message = "请选择领取方式")
    private CouponMode mode;

    @Schema(description = "优惠券类型 1:抵扣券 2:折扣券", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "请选择优惠券类型")
    private CouponType couponType;

    @Schema(description = "使用范围  1:店铺通用 2:指定商品", requiredMode = Schema.RequiredMode.REQUIRED)
    @OptionInt(value = {1, 2}, message = "请选择使用范围")
    private Integer useScope;

    @Schema(description = "店铺id")
    @NotNull(message = "请选择店铺")
    private Long storeId;

    @Schema(description = "抵扣金额 单位:元")
    @JsonDeserialize(using = YuanToCentDeserializer.class)
    private Integer deductionValue;

    @Schema(description = "折扣比例 10-99")
    @RangeInt(min = 10, max = 99, required = false, message = "折扣比例应为10~99之间")
    private Integer discountValue;

    @Schema(description = "使用门槛 0:不限制 大于0表示限制启用金额 单位:元")
    @JsonDeserialize(using = YuanToCentDeserializer.class)
    private Integer useThreshold;

    @Schema(description = "商品类型 ticket:门票 homestay:民宿 voucher:餐饮券 item:零售 line:线路 venue:场馆")
    private ProductType productType;

    @Schema(description = "发放开始时间 yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime startTime;

    @Schema(description = "发放截止时间 yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime endTime;

    @Schema(description = "可以使用的开始时间 yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime useStartTime;

    @Schema(description = "可以使用的截止时间 yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime useEndTime;

    @Schema(description = "使用说明")
    @WordChecker(message = "使用说明存在敏感词")
    @Size(max = 50, message = "使用说明最大50字符")
    @NotBlank(message = "使用说明不能为空")
    private String instruction;

    @Schema(description = "关联的商品列表")
    private List<Long> productIds;

    @Assign
    @Schema(description = "商户id", hidden = true)
    private Long merchantId;
}
