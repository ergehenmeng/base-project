package com.eghm.vo.business.scenic.ticket;

import com.eghm.convertor.CentToYuanSerializer;
import com.eghm.enums.TicketType;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 门票基础信息
 *
 * @author 二哥很猛
 * @since 2022/7/12
 */
@Data
public class TicketBaseVO {

    @ApiModelProperty("门票id")
    private Long id;

    @ApiModelProperty(value = "门票名称")
    private String title;

    @ApiModelProperty(value = "门票种类 1:成人 2:老人 3:儿童  4:演出 5:活动 6:研学 7:组合")
    private TicketType category;

    @ApiModelProperty(value = "划线价")
    @JsonSerialize(using = CentToYuanSerializer.class)
    private Integer linePrice;

    @ApiModelProperty(value = "销售价")
    @JsonSerialize(using = CentToYuanSerializer.class)
    private Integer salePrice;

    @ApiModelProperty(value = "剩余库存, 0:为售罄")
    private Integer stock;
}
