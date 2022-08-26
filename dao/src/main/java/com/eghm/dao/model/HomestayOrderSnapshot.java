package com.eghm.dao.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * <p>
 * 房态快照表
 * </p>
 *
 * @author 二哥很猛
 * @since 2022-08-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("homestay_order_snapshot")
@ApiModel(value="HomestayOrderSnapshot对象", description="房态快照表")
public class HomestayOrderSnapshot extends BaseEntity implements Serializable {

    @ApiModelProperty(value = "房型id")
    private Long homestayRoomId;

    @ApiModelProperty(value = "订单编号")
    private String orderNo;

    @ApiModelProperty(value = "销售价")
    private Integer salePrice;

    @ApiModelProperty(value = "划线价")
    private Integer linePrice;

    @ApiModelProperty(value = "日期")
    private LocalDate configDate;

}
