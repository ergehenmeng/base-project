package com.eghm.dto.ext;

import com.eghm.enums.DataType;
import com.eghm.enums.UserType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 管理后台登录人信息
 *
 * @author 殿小二
 * @since 2020/8/28
 */
@Data
public class UserToken {

    @Schema(description = "用户id")
    private Long id;

    @Schema(description = "0: 系统管理员 1: 系统用户")
    private UserType userType;

    @Schema(description = "昵称")
    private String nickName;

    @Schema(description = "数据权限类型(平台用户专属)")
    private DataType dataType;

    @Schema(description = "用户所属部门编号(平台用户专属)")
    private String deptCode;

    @Schema(description = "用户所拥有权限部门编号(平台用户专属)")
    private List<String> dataList;

    @Schema(description = "权限标示符")
    private List<String> authList;

    @Schema(description = "登录token")
    private String token;
}
