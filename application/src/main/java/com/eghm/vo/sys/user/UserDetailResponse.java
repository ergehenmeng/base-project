package com.eghm.vo.sys.user;

import com.eghm.enums.DataType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2022/5/3 16:27
 */
@Data
public class UserDetailResponse {

    @Schema(description = "id")
    private Long id;

    @Schema(description = "用户姓名")
    private String nickName;

    @Schema(description = "账户名(登陆账户)")
    private String userName;

    @Schema(description = "手机号码(登陆账户)")
    private String mobile;

    @Schema(description = "用户状态 0:锁定 1:正常")
    private Integer state;

    @Schema(description = "部门编号")
    private String deptCode;

    @Schema(description = "权限类型")
    private DataType dataType;

    @Schema(description = "备注信息")
    private String remark;

    @Schema(description = "角色列表")
    private List<Long> roleIds;
}
