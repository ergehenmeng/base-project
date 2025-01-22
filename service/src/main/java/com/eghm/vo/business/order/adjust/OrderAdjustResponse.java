package com.eghm.vo.business.order.adjust;

import com.eghm.convertor.CentToYuanSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @since 2024/3/25
 */

@Data
public class OrderAdjustResponse {

    @Schema(description = "商品名称")
    private String productName;

    @Schema(description = "操作人姓名")
    private String userName;

    @Schema(description = "原价格")
    @JsonSerialize(using = CentToYuanSerializer.class)
    private Integer sourcePrice;

    @Schema(description = "新价格")
    @JsonSerialize(using = CentToYuanSerializer.class)
    private Integer targetPrice;

    @Schema(description = "改价时间")
    @JsonFormat(pattern = "MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}
