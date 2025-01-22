package com.eghm.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.convertor.CentToYuanSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 房间价格配置表
 * </p>
 *
 * @author 二哥很猛
 * @since 2022-06-25
 */
@Data
@TableName("homestay_room_config")
public class HomestayRoomConfig {

    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "id主键")
    private Long id;

    @Schema(description = "房型id")
    private Long homestayRoomId;

    @Schema(description = "民宿id")
    private Long homestayId;

    @Schema(description = "状态 false:不可预定 true:可预定")
    private Boolean state;

    @Schema(description = "日期")
    private LocalDate configDate;

    @Schema(description = "划线价")
    @JsonSerialize(using = CentToYuanSerializer.class)
    private Integer linePrice;

    @Schema(description = "销售价")
    @JsonSerialize(using = CentToYuanSerializer.class)
    private Integer salePrice;

    @Schema(description = "剩余库存")
    private Integer stock;

    @Schema(description = "已预订数量")
    private Integer saleNum;

    @Schema(description = "添加日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @Schema(description = "更新日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
