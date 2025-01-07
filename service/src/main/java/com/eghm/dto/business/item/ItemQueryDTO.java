package com.eghm.dto.business.item;

import com.eghm.dto.ext.PagingQuery;
import com.eghm.enums.ref.DeliveryType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 二哥很猛
 * @since 2023/1/29
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ItemQueryDTO extends PagingQuery {

    @Schema(description = "交付方式 1:快递包邮 2:自提")
    private DeliveryType deliveryType;

    @Schema(description = "排序规则 0:默认排序 1:按价格排序 2:按销售量排序 3:好评率")
    private Integer sortBy;

    @Schema(description = "标签id")
    private String tagId;

}
