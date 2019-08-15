package com.fanyin.dto.system.role;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 二哥很猛
 * @date 2018/11/26 16:17
 */
@Data
public class RoleAddRequest implements Serializable {

    private static final long serialVersionUID = 8213867116327534945L;

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
