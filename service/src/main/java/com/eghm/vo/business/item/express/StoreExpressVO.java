package com.eghm.vo.business.item.express;

import com.eghm.convertor.CentToYuanSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 二哥很猛
 * @since 2023/8/25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreExpressVO {

    @ApiModelProperty("店铺id")
    private Long storeId;

    @ApiModelProperty("快递费")
    @JsonSerialize(using = CentToYuanSerializer.class)
    private Integer expressFee;
}
