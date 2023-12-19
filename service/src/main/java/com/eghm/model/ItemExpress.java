package com.eghm.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @since 2023/11/27
 */
@Data
@TableName("item_order_express")
@EqualsAndHashCode(callSuper = true)
public class ItemExpress extends BaseEntity {

    @ApiModelProperty("快递单号")
    private String expressNo;

    @ApiModelProperty("快递公司编码")
    private String expressCode;

    @ApiModelProperty("物流信息(json)")
    private String content;

    @ApiModelProperty("订单号")
    private String orderNo;

}
