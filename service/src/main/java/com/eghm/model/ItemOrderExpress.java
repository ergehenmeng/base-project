package com.eghm.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @since 2023/11/27
 */
@Data
@TableName("item_order_express")
public class ItemOrderExpress {

    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty("id主键")
    private Long id;

    @ApiModelProperty("零售订单id")
    private Long itemOrderId;

    @ApiModelProperty("快递信息id")
    private Long expressId;

    @ApiModelProperty("订单号")
    private String orderNo;

    @ApiModelProperty("添加时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

}
