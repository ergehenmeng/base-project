package com.eghm.dto.business.item.sku;

import com.eghm.validation.annotation.OptionInt;
import com.eghm.validation.annotation.WordChecker;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author 殿小二
 * @since 2023/3/6
 */

@Data
public class ItemSpecRequest {

    @ApiModelProperty("id(编辑时不能为空)")
    private Long id;

    @ApiModelProperty(value = "规格名", required = true)
    @Size(min = 1, max = 20, message = "规格名长度1~20位")
    @NotBlank(message = "规格名不能为空")
    @WordChecker(message = "规格名存在敏感词")
    private String specName;

    @ApiModelProperty(value = "规格值", required = true)
    @Size(min = 1, max = 20, message = "规格值长度1~20位")
    @NotBlank(message = "规格值不能为空")
    @WordChecker(message = "规格值存在敏感词")
    private String specValue;

    @ApiModelProperty(value = "规格图片(一级规格必填), 优先级比sku_pic低", required = true)
    private String specPic;

    @ApiModelProperty(value = "标签级别(1:一级标签 2:二级标签)", required = true)
    @OptionInt(value = {1, 2}, message = "标签级别格式错误")
    private Integer level;
}
