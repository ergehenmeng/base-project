package com.eghm.dto.business.order.item;

import com.eghm.state.machine.dto.ItemDTO;
import com.eghm.validation.annotation.OptionInt;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author wyb
 * @since 2023/5/5
 */
@Data
public class ItemOrderCreateDTO {

    @ApiModelProperty(value = "收货地址id", required = true)
    private Long addressId;

    @ApiModelProperty(value = "店铺商品信息", required = true)
    @NotEmpty(message = "请选择商品")
    private List<ItemDTO> itemList;

    @ApiModelProperty(value = "配送方式 1:快递 2:自提 ")
    @OptionInt(value = {1, 2}, message = "请选择配送方式")
    private Integer deliveryType;

    @ApiModelProperty("拼团活动编号")
    private String bookingNo;

}
