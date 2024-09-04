package com.eghm.dto.business.order.item;

import com.eghm.service.business.handler.dto.ItemDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author wyb
 * @since 2023/5/5
 */
@Data
public class ItemOrderCreateDTO {

    @ApiModelProperty(value = "收货地址id", required = true)
    @NotNull(message = "收货地址不能为空")
    private Long addressId;

    @ApiModelProperty(value = "店铺商品信息", required = true)
    @NotEmpty(message = "请选择商品")
    private List<ItemDTO> itemList;

    @ApiModelProperty("拼团活动编号")
    private String bookingNo;

}
