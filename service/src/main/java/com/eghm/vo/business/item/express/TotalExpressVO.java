package com.eghm.vo.business.item.express;

import com.eghm.convertor.CentToYuanEncoder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2023/8/25
 */
@Data
public class TotalExpressVO {

    @ApiModelProperty("总快递费")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer totalFee;

    @ApiModelProperty("每个店铺的快递费")
    private List<StoreExpressVO> storeList;
}
