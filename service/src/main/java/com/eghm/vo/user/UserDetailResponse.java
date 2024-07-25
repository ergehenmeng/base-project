package com.eghm.vo.user;

import com.eghm.enums.DataType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2022/5/3 16:27
 */
@Data
public class UserDetailResponse {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("用户姓名")
    private String nickName;

    @ApiModelProperty("手机号码(登陆账户)")
    private String mobile;

    @ApiModelProperty("用户状态 0:锁定 1:正常")
    private Integer state;

    @ApiModelProperty("部门编号")
    private String deptCode;

    @ApiModelProperty("权限类型")
    private DataType dataType;

    @ApiModelProperty("备注信息")
    private String remark;

    @ApiModelProperty("角色列表")
    private List<Long> roleIds;
}
