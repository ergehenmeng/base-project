package com.eghm.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.convertor.CentToYuanEncoder;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
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
public class HomestayRoomConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty("id主键")
    private Long id;

    @ApiModelProperty(value = "房型id")
    private Long homestayRoomId;

    @ApiModelProperty(value = "民宿id")
    private Long homestayId;

    @ApiModelProperty("状态 false:不可预定 true:可预定")
    private Boolean state;

    @ApiModelProperty(value = "日期")
    private LocalDate configDate;

    @ApiModelProperty(value = "划线价")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer linePrice;

    @ApiModelProperty(value = "销售价")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer salePrice;

    @ApiModelProperty(value = "剩余库存")
    private Integer stock;

    @ApiModelProperty(value = "已预订数量")
    private Integer saleNum;

    @ApiModelProperty("添加日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty("更新日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
