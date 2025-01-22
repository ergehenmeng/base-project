package com.eghm.vo.sys;

import com.eghm.enums.RoleType;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @since 2024/7/25
 */

@Data
public class SysRoleResponse {

    @Schema(description = "id主键")
    private Long id;

    @Schema(description = "角色名称")
    private String roleName;

    @Schema(description = "角色类型 common:通用角色 scenic:景区 homestay:民宿 voucher:餐饮 item:零售 line:线路 venue:场馆 merchant:商户角色")
    private RoleType roleType;

    @Schema(description = "备注信息")
    private String remark;

    @Schema(description = "添加时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
