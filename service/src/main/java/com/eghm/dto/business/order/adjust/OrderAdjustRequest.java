package com.eghm.dto.business.order.adjust;

import com.eghm.dto.ext.ActionRecord;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2024/3/25
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class OrderAdjustRequest extends ActionRecord {

    @ApiModelProperty(value = "订单编号")
    @NotBlank(message = "订单编号不能为空")
    private String orderNo;

    @ApiModelProperty(value = "改价列表")
    @NotEmpty(message = "请选择要改价的商品")
    private List<ItemAdjustRequest> priceList;
}
