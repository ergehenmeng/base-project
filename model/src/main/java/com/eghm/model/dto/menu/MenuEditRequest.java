package com.eghm.model.dto.menu;

import com.eghm.model.validation.annotation.OptionInt;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author 二哥很猛
 */
@Data
public class MenuEditRequest implements Serializable {

    private static final long serialVersionUID = 6714241304584747778L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "id", required = true)
    @NotNull(message = "id名称不能为空")
    private Long id;

    @ApiModelProperty(value = "菜单名称", required = true)
    @NotNull(message = "菜单名称不能为空")
    private String title;

    @ApiModelProperty(value = "标识符", required = true)
    @NotNull(message = "标识符不能为空")
    private String nid;

    @ApiModelProperty(value = "菜单等级 1:一级菜单 2:二级菜单 3:三级菜单(按钮菜单)", required = true)
    @OptionInt(value = {1, 2, 3}, message = "菜单等级不合法")
    private Byte grade;

    @ApiModelProperty(value = "父菜单id", required = true)
    @NotNull(message = "父菜单id不能为空")
    private Long pid;

    @ApiModelProperty(value = "菜单url")
    private String url;

    @ApiModelProperty(value = "子菜单url(逗号分割)")
    private String subUrl;

    @ApiModelProperty(value = "排序(小-大)")
    private Integer sort;

    @ApiModelProperty(value = "备注信息")
    private String remark;

}
