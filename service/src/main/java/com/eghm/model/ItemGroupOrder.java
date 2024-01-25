package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 拼团订单表
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-01-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("item_group_order")
public class ItemGroupOrder extends BaseEntity {

    @ApiModelProperty(value = "拼团活动编号")
    private String bookingNo;

    @ApiModelProperty("状态 0:拼团中 1:拼团成功 2:拼团失败")
    private Integer state;

    @ApiModelProperty("是否为拼团发起者")
    private Boolean starter;

    @ApiModelProperty(value = "订单号")
    private String orderNo;

    @ApiModelProperty(value = "零售id")
    private Long itemId;

    @ApiModelProperty(value = "会员id")
    private Long memberId;

    @ApiModelProperty("拼团活动id")
    private Long bookingId;

}
