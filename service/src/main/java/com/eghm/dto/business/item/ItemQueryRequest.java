package com.eghm.dto.business.item;

import com.eghm.annotation.Assign;
import com.eghm.annotation.YuanToCentFormat;
import com.eghm.dto.ext.PagingQuery;
import com.eghm.enums.ref.DeliveryType;
import com.eghm.enums.ref.State;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 二哥很猛
 * @since 2022/7/1
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ItemQueryRequest extends PagingQuery {

    @Schema(description = "快递模板id")
    private Long expressId;

    @Schema(description = "状态")
    private State state;

    @Schema(description = "交付方式 1:快递包邮 2:自提")
    private DeliveryType deliveryType;

    @Schema(description = "标签id")
    private String tagId;

    @Schema(description = "店铺id")
    private Long storeId;

    @Schema(description = "最低价格")
    @YuanToCentFormat
    private Integer minPrice;

    @Schema(description = "最高价格")
    @YuanToCentFormat
    private Integer maxPrice;

    @Assign
    @Schema(description = "商户id", hidden = true)
    private Long merchantId;
}
