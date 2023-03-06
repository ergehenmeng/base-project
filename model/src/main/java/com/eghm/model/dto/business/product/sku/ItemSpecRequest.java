package com.eghm.model.dto.business.product.sku;

import com.eghm.model.validation.annotation.OptionInt;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author 殿小二
 * @date 2023/3/6
 */

@Data
public class ItemSpecRequest {
    
    @ApiModelProperty(value = "规格名")
    @Size(min = 1, max = 20, message = "规格名长度1~20位")
    private String specName;
    
    @ApiModelProperty(value = "规格值")
    @Size(min = 1, max = 20, message = "规格值长度1~20位")
    private String specValue;
    
    @ApiModelProperty(value = "规格图片(一级规格必填), 优先级比sku_pic低")
    @NotBlank(message = "规格图片不能为空")
    private String specPic;
    
    @ApiModelProperty(value = "标签级别(1:一级标签 2:二级标签)")
    @OptionInt(value = {1, 2}, message = "标签级别格式错误")
    private Integer level;
}
