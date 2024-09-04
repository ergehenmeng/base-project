package com.eghm.service.business.handler.context;

import com.eghm.annotation.Assign;
import com.eghm.dto.ext.AsyncKey;
import com.eghm.model.Item;
import com.eghm.service.business.handler.dto.ItemDTO;
import com.eghm.state.machine.Context;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author 二哥很猛
 * @since 2022/7/27
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ItemOrderCreateContext extends AsyncKey implements Context {

    @ApiModelProperty("商品信息按店铺分组")
    private List<ItemDTO> itemList;

    @ApiModelProperty("拼团活动编号(团长发起拼团时为空, 团员拼团时该字段不能为空)")
    private String bookingNo;

    @ApiModelProperty("收货地址id")
    private Long addressId;

    @ApiModelProperty(value = "是否为拼团订单(拼团活动只能选一个店铺的一个商品)", hidden = true)
    @Assign
    private Boolean groupBooking;

    @ApiModelProperty("源状态")
    @Assign
    private Integer from;

    @ApiModelProperty(value = "订单编号(值传递,多个逗号分隔)", hidden = true)
    @Assign
    private String orderNo;

    @Assign
    @ApiModelProperty(value = "用户id", hidden = true)
    private Long memberId;

    @ApiModelProperty(value = "已拼单数量(承载数据,值传递)", hidden = true)
    @Assign
    private Integer bookingNum;

    @ApiModelProperty(value = "拼团id(承载数据,值传递)", hidden = true)
    @Assign
    private Long bookingId;

    @ApiModelProperty(value = "是否为拼团发起者(承载数据,值传递)", hidden = true)
    @Assign
    private Boolean starter = false;

    @ApiModelProperty(value = "拼团过期时间(承载数据,值传递)", hidden = true)
    @Assign
    private Integer expireTime;

    @ApiModelProperty(value = "限时购活动id(承载数据,值传递)", hidden = true)
    @Assign
    private Long limitId;

    @ApiModelProperty(value = "商品信息(承载数据,减少后续重复查询)", hidden = true)
    @Assign
    private Map<Long, Item> itemMap;

    @ApiModelProperty(value = "商品id(承载数据,减少后续重复运算)", hidden = true)
    @Assign
    private Set<Long> itemIds;

    @ApiModelProperty(value = "商品skuId(承载数据,减少后续重复运算)", hidden = true)
    @Assign
    private Set<Long> skuIds;

    @ApiModelProperty(value = "使用的总积分(承载数据,减少后续重复运算)", hidden = true)
    @Assign
    private Integer totalScore;
}
