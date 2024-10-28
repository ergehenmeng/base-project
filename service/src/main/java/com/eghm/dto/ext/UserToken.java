package com.eghm.dto.ext;

import com.eghm.enums.DataType;
import com.eghm.enums.UserType;
import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty("用户id")
    private Long id;

    @ApiModelProperty("0: 系统管理员 1: 系统用户")
    private UserType userType;

    @ApiModelProperty("昵称")
    private String nickName;

    @ApiModelProperty("数据权限类型(平台用户专属)")
    private DataType dataType;

    @ApiModelProperty("用户所属部门编号(平台用户专属)")
    private String deptCode;

    @ApiModelProperty("用户所拥有权限部门编号(平台用户专属)")
    private List<String> dataList;

    @ApiModelProperty("权限标示符")
    private List<String> authList;

    @ApiModelProperty("登录token")
    private String token;
}
