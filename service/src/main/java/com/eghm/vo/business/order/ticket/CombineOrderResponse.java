package com.eghm.vo.business.order.ticket;

import com.eghm.enums.TicketType;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @since 2025/5/13
 */
@Data
public class CombineOrderResponse {

    @Schema(description = "门票名称")
    private String title;

    @Schema(description = "门票种类 1:成人 2:老人 3:儿童  4:演出 5:活动 6:研学")
    private TicketType category;

    @Schema(description = "使用时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime useTime;
}
