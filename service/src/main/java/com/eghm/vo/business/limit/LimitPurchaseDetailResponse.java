package com.eghm.vo.business.limit;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2024/1/29
 */

@Data
public class LimitPurchaseDetailResponse {

    @ApiModelProperty("id主键")
    private Long id;

    @ApiModelProperty(value = "活动名称")
    private String title;

    @ApiModelProperty(value = "开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime endTime;

    @ApiModelProperty(value = "提前预告小时")
    private Integer advanceHour;

    @ApiModelProperty("规格优惠列表")
    private List<LimitSkuResponse> skuList;

    @ApiModelProperty("商品id列表")
    private List<Long> itemIds;

    @ApiModelProperty("备注")
    private String remark;
}
