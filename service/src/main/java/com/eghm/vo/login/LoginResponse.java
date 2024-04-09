package com.eghm.vo.login;

import com.eghm.vo.menu.MenuResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2022/1/28 18:23
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginResponse {

    @ApiModelProperty("token")
    private String token;

    @ApiModelProperty("用户类型 1:系统用户 2:商户用户")
    private Integer userType;

    @ApiModelProperty("昵称")
    private String nickName;

    @ApiModelProperty("按钮权限列表")
    private List<String> buttonList;

    @ApiModelProperty("左侧菜单")
    private List<MenuResponse> leftMenuList;
}
