package com.eghm.dao.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.eghm.common.convertor.CentToYuanEncoder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 房间价格配置表
 * </p>
 *
 * @author 二哥很猛
 * @since 2022-06-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("homestay_room_config")
@ApiModel(value="HomestayRoomConfig对象", description="房间价格配置表")
public class HomestayRoomConfig extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "房型id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long homestayRoomId;

    @ApiModelProperty("状态 false:不可预定 true:可预定")
    private Boolean state;

    @ApiModelProperty(value = "日期")
    private LocalDate configDate;

    @ApiModelProperty(value = "划线机")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer linePrice;

    @ApiModelProperty(value = "销售价")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer salePrice;

    @ApiModelProperty(value = "剩余库存")
    private Integer stock;

    @ApiModelProperty(value = "已预订数量")
    private Integer saleNum;

}
