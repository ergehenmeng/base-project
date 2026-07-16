package com.eghm.platform.iam.vo;

import com.eghm.platform.iam.vo.MenuTreeResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2025/8/22
 */
@Data
public class LoginMenuResponse {

    @Schema(description = "按钮权限列表")
    private List<String> permList;

    @Schema(description = "菜单权限列表")
    private List<MenuTreeResponse> menuList;
}
