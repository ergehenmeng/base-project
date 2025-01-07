package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 门票订单表
 * </p>
 *
 * @author 二哥很猛
 * @since 2022-07-12
 */
@Data
@TableName("ticket_order")
@EqualsAndHashCode(callSuper = true)
public class TicketOrder extends BaseEntity {

    @Schema(description = "门票所属景区(冗余字段)")
    private Long scenicId;

    @Schema(description = "景区名称")
    private String scenicName;

    @Schema(description = "门票id")
    private Long ticketId;

    @Schema(description = "订单号")
    private String orderNo;

    @Schema(description = "划线价")
    private Integer linePrice;

    @Schema(description = "预计游玩日期")
    private LocalDate visitDate;

    @Schema(description = "门票种类 1:成人 2:老人 3:儿童  4:演出 5:活动 6:研学 7:组合")
    private Integer category;

    @Schema(description = "是否实名购票 0:不实名 1:实名")
    private Boolean realBuy;

    @Schema(description = "门票介绍")
    private String introduce;

    @Schema(description = "门票核销时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime useTime;
}
