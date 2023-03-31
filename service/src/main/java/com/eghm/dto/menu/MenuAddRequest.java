package com.eghm.dto.menu;

import com.eghm.validation.annotation.OptionInt;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 菜单添加
 * @author 二哥很猛
 * @date  2018/1/30 11:36
 */
@Data
public class MenuAddRequest implements Serializable {

    private static final long serialVersionUID = 8515421119279590820L;

    @ApiModelProperty(value = "菜单名称", required = true)
    @NotBlank(message = "菜单名称不能为空")
    private String title;

    @ApiModelProperty(value = "菜单等级 1:导航菜单 2:按钮菜单", required = true)
    @OptionInt(value = {1, 2}, message = "菜单等级不合法")
    private Integer grade;

    @ApiModelProperty(value = "父菜单id", required = true)
    @NotBlank(message = "父菜单id不能为空")
    private String pid;

    @ApiModelProperty(value = "菜单url")
    private String path;

    @ApiModelProperty(value = "子菜单url(逗号分割)")
    private String subPath;

    @ApiModelProperty(value = "排序(小-大)")
    private Integer sort;

    @ApiModelProperty(value = "备注信息")
    private String remark;

}
