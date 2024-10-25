package com.eghm.vo.login;

import com.eghm.enums.UserType;
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

    @ApiModelProperty("用户类型 0:系统管理员 1:系统用户")
    private UserType userType;

    @ApiModelProperty("昵称")
    private String nickName;

    @ApiModelProperty("是否初始密码")
    private Boolean init;

    @ApiModelProperty("密码是否过期(90天没修改即过期)")
    private Boolean expire;

    @ApiModelProperty("按钮权限列表")
    private List<String> permList;

    @ApiModelProperty("菜单权限列表")
    private List<MenuResponse> menuList;
}
