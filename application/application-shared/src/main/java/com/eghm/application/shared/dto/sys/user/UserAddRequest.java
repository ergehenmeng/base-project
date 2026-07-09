package com.eghm.application.shared.dto.sys.user;

import com.eghm.domain.shared.enums.DataType;
import com.eghm.application.shared.validation.annotation.Mobile;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.List;

/**
 * 管理人员 添加
 *
 * @author 二哥很猛
 * @since 2018/11/30 15:43
 */
@Data
public class UserAddRequest {

    @Schema(description = "用户名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "用户名称不能为空")
    @Size(max = 10, message = "用户名称最多10字符")
    private String nickName;

    @Schema(description = "账户名", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "账户名不能为空")
    @Length(min = 6, max = 15, message = "账户名长度6-15位")
    private String userName;

    @Schema(description = "手机号", requiredMode = Schema.RequiredMode.REQUIRED)
    @Mobile
    private String mobile;

    @Schema(description = "所属部门")
    private String deptCode;

    @Schema(description = "角色编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "请选择角色")
    private List<Long> roleIds;

    @Schema(description = "数据权限(1:本人 2:本部门 4:本部门及子部门 8:全部 16:自定义)")
    private DataType dataType;

    @Schema(description = "数据权限部门id []")
    private List<String> deptIds;

    @Schema(description = "备注信息")
    @Size(max = 100, message = "备注信息最大100字符")
    private String remark;
}
