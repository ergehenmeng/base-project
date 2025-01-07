package com.eghm.dto.sys.menu;

import com.eghm.validation.annotation.OptionInt;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;

/**
 * 菜单添加
 *
 * @author 二哥很猛
 * @since 2018/1/30 11:36
 */
@Data
public class MenuAddRequest {

    @Schema(description = "菜单名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "菜单名称不能为空")
    private String title;

    @Schema(description = "图标")
    private String icon;

    @Schema(description = "菜单分类 1:导航菜单 2:按钮菜单", requiredMode = Schema.RequiredMode.REQUIRED)
    @OptionInt(value = {1, 2}, message = "菜单等级不合法")
    private Integer grade;

    @Schema(description = "父菜单id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "父菜单id不能为空")
    private String pid;

    @Schema(description = "路由地址")
    @Length(max = 100, message = "路由地址最大100字符")
    private String path;

    @Schema(description = "权限URL(逗号分割)")
    @Length(max = 300, message = "权限URL最大300字符")
    private String subPath;

    @Schema(description = "菜单权限: 1:商户菜单(该菜单或按钮只对商户开放) 2:系统菜单(该菜单或按钮只对系统人员开放) 3:通用菜单(该菜单或按钮对商户和系统人员都开放)")
    @OptionInt(value = {1, 2, 3}, message = "菜单权限不合法")
    private Integer displayState;

    @Schema(description = "备注信息")
    private String remark;

}
