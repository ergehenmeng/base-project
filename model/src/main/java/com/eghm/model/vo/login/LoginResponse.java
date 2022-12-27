package com.eghm.model.vo.login;

import com.eghm.model.vo.menu.MenuResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2022/1/28 18:23
 */
@Data
public class LoginResponse {

    @ApiModelProperty("token")
    private String token;

    @ApiModelProperty("按钮权限列表")
    private List<String> buttonList;

    @ApiModelProperty("左侧菜单")
    private List<MenuResponse> leftMenuList;
}
