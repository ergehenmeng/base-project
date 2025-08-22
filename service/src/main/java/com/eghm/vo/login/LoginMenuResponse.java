package com.eghm.vo.login;

import com.eghm.vo.sys.menu.MenuResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2025/8/22
 */
@Data
public class LoginMenuResponse {

    @ApiModelProperty(value = "按钮权限列表")
    private List<String> permList;

    @ApiModelProperty(value = "菜单权限列表")
    private List<MenuResponse> menuList;
}
