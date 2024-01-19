package com.eghm.dto.business.item;

import com.eghm.annotation.Assign;
import com.eghm.configuration.annotation.YuanToCentFormat;
import com.eghm.dto.ext.PagingQuery;
import com.eghm.enums.ref.DeliveryType;
import com.eghm.enums.ref.State;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 二哥很猛
 * @since 2022/7/1
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ItemQueryRequest extends PagingQuery {

    @ApiModelProperty("快递模板id")
    private Long expressId;

    @ApiModelProperty(value = "状态")
    private State state;

    @ApiModelProperty(value = "交付方式")
    private DeliveryType deliveryType;

    @ApiModelProperty("标签id")
    private String tagId;

    @ApiModelProperty("店铺id")
    private Long storeId;

    @ApiModelProperty("最低价格")
    @YuanToCentFormat
    private Integer minPrice;

    @ApiModelProperty("最高价格")
    @YuanToCentFormat
    private Integer maxPrice;

    @Assign
    @ApiModelProperty(value = "商户id", hidden = true)
    private Long merchantId;
}
