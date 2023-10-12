package com.eghm.dto.business.item;

import com.eghm.annotation.Assign;
import com.eghm.dto.ext.PagingQuery;
import com.eghm.enums.ref.DeliveryType;
import com.eghm.enums.ref.State;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 二哥很猛
 * @date 2022/7/1
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ItemQueryRequest extends PagingQuery {

    @ApiModelProperty("快递模板id")
    private Long expressId;

    @ApiModelProperty(value = "状态 0:待上架 1:已上架 2:强制下架")
    private State state;

    @ApiModelProperty(value = "交付方式 1:门店自提 2:快递包邮")
    private DeliveryType deliveryType;

    @ApiModelProperty("标签id")
    private String tagId;

    @ApiModelProperty("店铺id")
    private Long storeId;

    @Assign
    @ApiModelProperty(value = "商户id", hidden = true)
    private Long merchantId;
}
