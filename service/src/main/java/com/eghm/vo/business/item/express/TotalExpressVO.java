package com.eghm.vo.business.item.express;

import com.eghm.convertor.CentToYuanSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2023/8/25
 */
@Data
public class TotalExpressVO {

    @Schema(description = "总快递费")
    @JsonSerialize(using = CentToYuanSerializer.class)
    private Integer totalFee;

    @Schema(description = "每个店铺的快递费")
    private List<StoreExpressVO> storeList;
}
