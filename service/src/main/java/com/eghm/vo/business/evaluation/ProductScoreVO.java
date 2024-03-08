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
public class ProductScoreVO {

    @ApiModelProperty("商品id")
    private Long productId;

    @ApiModelProperty("商品类型 ticket:门票 homestay:民宿 restaurant:餐饮券 item:零售 line:线路 venue:场馆")
    private ProductType productType;

    @ApiModelProperty("分数")
    private BigDecimal score;
}
