package com.eghm.dto.business.purchase;

import com.eghm.validation.annotation.RangeInt;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2024/1/26
 */

@Data
public class LimitPurchaseEditRequest {

    @ApiModelProperty(value = "活动id")
    @NotNull(message = "活动id不能为空")
    private Long id;

    @ApiModelProperty(value = "活动名称")
    @NotBlank(message = "活动名称不能为空")
    @Size(max = 20, message = "活动名称最大20字符")
    private String title;

    @ApiModelProperty(value = "开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "开始时间不能为空")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "结束时间")
    @NotNull(message = "结束时间不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    @ApiModelProperty(value = "提前预告小时")
    @RangeInt(max = 72, message = "提前预告不能大于72小时")
    private Integer advanceHour;

    @ApiModelProperty(value = "商品列表")
    @NotEmpty(message = "请选择商品列表")
    private List<PurchaseItemRequest> itemList;
}
