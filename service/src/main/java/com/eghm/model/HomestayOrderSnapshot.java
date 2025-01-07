package com.eghm.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 房态快照表
 * </p>
 *
 * @author 二哥很猛
 * @since 2022-08-25
 */
@Data
@TableName("homestay_order_snapshot")
public class HomestayOrderSnapshot {

    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "id主键")
    private Long id;

    @Schema(description = "房型id")
    private Long homestayRoomId;

    @Schema(description = "订单编号")
    private String orderNo;

    @Schema(description = "销售价")
    private Integer salePrice;

    @Schema(description = "划线价")
    private Integer linePrice;

    @Schema(description = "日期")
    private LocalDate configDate;

    @Schema(description = "添加日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}
