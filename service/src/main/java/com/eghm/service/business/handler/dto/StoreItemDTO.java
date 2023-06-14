package com.eghm.service.business.handler.dto;

import com.eghm.validation.annotation.RangeInt;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author 二哥很猛
 * @date 2022/9/5
 */
@Data
public class StoreItemDTO implements Serializable {

    @ApiModelProperty("店铺id")
    @NotNull(message = "请选择待下单的店铺商品")
    private Long storeId;

    @ApiModelProperty("店铺下的商品列表")
    @NotEmpty(message = "请选择待下单的商品")
    private List<ItemDTO> itemList;
}
