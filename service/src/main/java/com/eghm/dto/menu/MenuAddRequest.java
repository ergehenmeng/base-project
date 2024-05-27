package com.eghm.dto.menu;

import com.eghm.validation.annotation.OptionInt;
import com.eghm.validation.annotation.RangeInt;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;

/**
 * 菜单添加
 *
 * @author 二哥很猛
 * @since 2018/1/30 11:36
 */
@Data
public class MenuAddRequest {

    @ApiModelProperty(value = "菜单名称", required = true)
    @NotBlank(message = "菜单名称不能为空")
    private String title;

    @ApiModelProperty("图标")
    private String icon;

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

    @ApiModelProperty("菜单类型: 1:商户菜单(该菜单或按钮只对商户开放) 2:系统菜单(该菜单或按钮只对系统人员开放) 3:通用菜单(该菜单或按钮对商户和系统人员都开放)")
    private Integer displayState;

    @ApiModelProperty(value = "备注信息")
    private String remark;

}
