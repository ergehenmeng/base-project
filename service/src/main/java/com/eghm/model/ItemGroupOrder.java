package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "拼团活动编号")
    private String bookingNo;

    @Schema(description = "状态 0:拼团中 1:拼团成功 2:拼团失败")
    private Integer state;

    @Schema(description = "是否为拼团发起者")
    private Boolean starter;

    @Schema(description = "订单号")
    private String orderNo;

    @Schema(description = "零售id")
    private Long itemId;

    @Schema(description = "会员id")
    private Long memberId;

    @Schema(description = "拼团活动id")
    private Long bookingId;

}
