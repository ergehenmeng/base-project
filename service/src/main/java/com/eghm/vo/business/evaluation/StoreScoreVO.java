package com.eghm.vo.business.evaluation;

import com.eghm.enums.ref.ProductType;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author 二哥很猛
 * @since 2023/8/28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreScoreVO {

    @ApiModelProperty("店铺Id")
    private Long storeId;

    @ApiModelProperty("商品类型")
    private ProductType productType;

    @ApiModelProperty("分数")
    private BigDecimal score;
}
