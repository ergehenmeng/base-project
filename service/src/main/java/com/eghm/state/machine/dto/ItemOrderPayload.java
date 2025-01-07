package com.eghm.state.machine.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2022/9/5
 */
@Data
public class ItemOrderPayload {

    @Schema(description = "按店铺分组的所有下单信息")
    private List<StoreOrderPackage> packageList;

}
