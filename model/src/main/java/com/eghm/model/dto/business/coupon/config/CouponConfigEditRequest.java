package com.eghm.model.dto.business.coupon.config;

import com.eghm.model.dto.business.coupon.product.CouponItemRequest;
import com.eghm.model.validation.annotation.RangeInt;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 二哥很猛
 * @date 2022/7/13
 */
@Data
public class CouponConfigEditRequest {

    @ApiModelProperty("id")
    @NotNull(message = "id不能为空")
    private Long id;

    @ApiModelProperty("优惠券名称")
    @Size(min = 2, max = 20, message = "优惠券名称长度2~20位")
    @NotBlank(message = "优惠券名称不能为空")
    private String title;

    @ApiModelProperty(value = "库存(发放数量)")
    @RangeInt(max = 9999, message = "库存应为0~9999")
    private Integer stock;

    @ApiModelProperty(value = "单人领取限制")
    @RangeInt(min = 1, max = 99, message = "单人领取限制1~99")
    private Integer maxLimit;

    @ApiModelProperty(value = "发放开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "发放截止时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime endTime;

    @ApiModelProperty(value = "使用说明")
    private String instruction;

    @ApiModelProperty("关联的商品列表")
    private List<CouponItemRequest> productList;
}
