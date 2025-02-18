package com.eghm.dto.business.order.item;

import com.eghm.state.machine.dto.ItemDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

/**
 * @author wyb
 * @since 2023/5/5
 */
@Data
public class ItemOrderCreateDTO {

    @Schema(description = "收货地址id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "收货地址不能为空")
    private Long addressId;

    @Schema(description = "店铺商品信息", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "请选择商品")
    private List<ItemDTO> itemList;

    @Schema(description = "拼团活动编号")
    private String bookingNo;

}
