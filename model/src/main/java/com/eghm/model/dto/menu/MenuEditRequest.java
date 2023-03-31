package com.eghm.model.dto.menu;

import com.eghm.model.validation.annotation.OptionInt;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author 二哥很猛
 */
@Data
public class MenuEditRequest implements Serializable {

    private static final long serialVersionUID = 6714241304584747778L;

    @ApiModelProperty(value = "id", required = true)
    @NotNull(message = "id名称不能为空")
    private String id;

    @ApiModelProperty(value = "菜单名称", required = true)
    @NotBlank(message = "菜单名称不能为空")
    private String title;

    @ApiModelProperty(value = "菜单等级 1:导航菜单 2:按钮菜单", required = true)
    @OptionInt(value = {1, 2}, message = "菜单等级不合法")
    private Integer grade;

    @ApiModelProperty(value = "菜单url")
    private String path;

    @ApiModelProperty(value = "子菜单url(逗号分割)")
    private String subPath;

    @ApiModelProperty(value = "排序(小-大)")
    private Integer sort;

    @ApiModelProperty(value = "备注信息")
    private String remark;

}
