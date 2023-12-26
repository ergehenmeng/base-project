package com.eghm.dto.business.item;

import com.eghm.validation.annotation.WordChecker;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @author 二哥很猛
 * @since 2023/8/9
 */
@Data
public class ItemTagEditRequest {

    @ApiModelProperty(value = "id不能为空", required = true)
    @NotBlank(message = "id不能为空")
    private String id;

    @ApiModelProperty(value = "标签名称", required = true)
    @NotBlank(message = "标签名称不能为空")
    @Length(min = 2, max = 8, message = "标签名称长度应为2~8字符")
    @WordChecker(message = "标签名称存在敏感词")
    private String title;

    @ApiModelProperty(value = "标签图标")
    private String icon;

    @ApiModelProperty("排序id")
    private Integer sort;
}
