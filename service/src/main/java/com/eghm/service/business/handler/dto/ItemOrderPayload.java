package com.eghm.service.business.handler.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2022/9/5
 */
@Data
public class ItemOrderPayload {

    @ApiModelProperty("按店铺分组的所有下单信息")
    private List<StoreOrderPackage> packageList;

}
