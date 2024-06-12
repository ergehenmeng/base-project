package com.eghm.dto.business.item.express;

import com.eghm.annotation.Assign;
import com.eghm.validation.annotation.OptionInt;
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
public class ExpressTemplateEditRequest {

    @ApiModelProperty(value = "id", required = true)
    @NotNull(message = "id不能为空")
    private Long id;

    @ApiModelProperty(value = "模板名称", required = true)
    @Size(min = 2, max = 20, message = "快递模板名称长度2~20位")
    @NotBlank(message = "快递模板名称不能为空")
    private String title;

    @ApiModelProperty(value = "状态 0:禁用 1:启用")
    @NotNull(message = "状态不能为空")
    private Integer state;

    @ApiModelProperty(value = "计费方式 1:按件数 2:按重量", required = true)
    @OptionInt(value = {1, 2}, message = "计费方式不合法")
    private Integer chargeMode;

    @ApiModelProperty(value = "配送区域", required = true)
    @NotEmpty(message = "配送区域不能为空")
    @Size(min = 1, max = 20, message = "配送区域不能超过20个")
    private List<ExpressTemplateRegionRequest> regionList;

    @Assign
    @ApiModelProperty(hidden = true, value = "登录人商户ID")
    private Long merchantId;
}
