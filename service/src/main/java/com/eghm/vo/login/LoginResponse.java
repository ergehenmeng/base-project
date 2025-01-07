package com.eghm.vo.login;

import com.eghm.enums.UserType;
import com.eghm.vo.menu.MenuResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2022/1/28 18:23
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginResponse {

    @Schema(description = "商户ID")
    private Long merchantId;

    @Schema(description = "token")
    private String token;

    @Schema(description = "用户类型 0:系统管理员 1:系统用户 2:商户管理员 3:商户用户")
    private UserType userType;

    @Schema(description = "商户类型 1 2 4 8 16 32")
    private Integer merchantType;

    @Schema(description = "系统名称")
    private String systemName;

    @Schema(description = "用户名")
    private String userName;

    @Schema(description = "昵称")
    private String nickName;

    @Schema(description = "是否绑定微信")
    private Boolean bindWechat;

    @Schema(description = "是否初始密码")
    private Boolean init;

    @Schema(description = "密码是否过期(90天没修改即过期)")
    private Boolean expire;

    @Schema(description = "按钮权限列表")
    private List<String> permList;

    @Schema(description = "菜单权限列表")
    private List<MenuResponse> menuList;
}
