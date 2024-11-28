package com.eghm.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.convertor.CentToYuanEncoder;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * <p>
 * 场馆场次价格信息表
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-01-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("venue_site_price")
public class VenueSitePrice {

    @ApiModelProperty(value = "主键")
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty(value = "所属场馆id")
    private Long venueId;

    @ApiModelProperty(value = "所属场地")
    private Long venueSiteId;

    @ApiModelProperty(value = "所属商户id")
    private Long merchantId;

    @ApiModelProperty(value = "开始时间")
    private LocalTime startTime;

    @ApiModelProperty(value = "结束时间")
    private LocalTime endTime;

    @ApiModelProperty("当前日期")
    private LocalDate nowDate;

    @ApiModelProperty("可预订数量 默认1")
    private Integer stock;

    @ApiModelProperty("星期几 1-7")
    private Integer nowWeek;

    @ApiModelProperty(value = "价格")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer price;

    @ApiModelProperty("是否可预定 false:不可预定 true:可预定")
    private Boolean state;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

}
