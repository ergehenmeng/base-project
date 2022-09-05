package com.eghm.model.dto.business.product;

import com.eghm.common.enums.ref.DeliveryType;
import com.eghm.common.enums.ref.PlatformState;
import com.eghm.common.enums.ref.State;
import com.eghm.model.dto.ext.PagingQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 二哥很猛
 * @date 2022/7/1
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ProductQueryRequest extends PagingQuery {


    @ApiModelProperty(value = "状态 0:待上架 1:已上架")
    private State state;

    @ApiModelProperty(value = "平台状态 0:初始 1:待审核 2:已上架")
    private PlatformState platformState;

    @ApiModelProperty(value = "交付方式 1:门店自提 2:快递包邮")
    private DeliveryType deliveryType;

}
