package com.eghm.dto.sys.user;

import com.eghm.validation.annotation.Mobile;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2018/11/30 16:37
 */
@Data
public class UserEditRequest {

    @Schema(description = "id主键", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "id不能为空")
    private Long id;

    @Schema(description = "用户名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "用户名称不能为空")
    private String nickName;

    @Schema(description = "账户名", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "账户名不能为空")
    @Length(min = 6, max = 15, message = "账户名长度6-15位")
    private String userName;

    @Schema(description = "手机号", requiredMode = Schema.RequiredMode.REQUIRED)
    @Mobile
    private String mobile;

    @Schema(description = "所属部门编号", requiredMode = Schema.RequiredMode.REQUIRED)
    private String deptCode;

    @Schema(description = "角色编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "请选择角色")
    private List<Long> roleIds;

    @Schema(description = "数据权限(1:本人 2:本部门 4:本部门及子部门 8:全部 16:自定义)")
    private Integer dataType;

    @Schema(description = "数据权限部门id,逗号分割")
    private String deptIds;

    @Schema(description = "备注信息")
    private String remark;
}
