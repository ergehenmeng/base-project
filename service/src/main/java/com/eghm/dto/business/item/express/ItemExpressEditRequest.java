package com.eghm.dto.business.item.express;

import com.eghm.annotation.Assign;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2023/8/22
 */
@Data
public class ItemExpressEditRequest {

    @ApiModelProperty("id")
    @NotNull(message = "id不能为空")
    private Long id;

    @ApiModelProperty(value = "模板名称")
    @Size(min = 2, max = 20, message = "快递模板名称长度2~20位")
    @NotBlank(message = "快递模板名称不能为空")
    private String title;

    @ApiModelProperty("配送区域")
    @NotEmpty(message = "配送区域不能为空")
    @Size(min = 1, max = 20, message = "配送区域不能超过20个")
    private List<ItemExpressRegionRequest> regionList;

    @Assign
    @ApiModelProperty(hidden = true, value = "登录人商户ID")
    private Long merchantId;
}
