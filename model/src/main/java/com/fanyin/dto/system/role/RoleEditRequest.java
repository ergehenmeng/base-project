package com.fanyin.dto.system.role;

import lombok.Data;

import java.io.Serializable;

/**
 * 编辑角色信息
 * @author 二哥很猛
 * @date 2018/11/26 16:08
 */
@Data
public class RoleEditRequest implements Serializable {

    private static final long serialVersionUID = -4613884225645951474L;

    /**
     * 主键
     */
    private Integer id;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色类型
     */
    private String roleType;

    /**
     * 备注信息
     */
    private String remark;

}
