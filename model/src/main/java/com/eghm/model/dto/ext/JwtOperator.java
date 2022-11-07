package com.eghm.model.dto.ext;

import lombok.Data;

import java.util.List;

/**
 * 管理员登录信息
 * @author 殿小二
 * @date 2020/8/28
 */
@Data
public class JwtOperator {

    /**
     * 用户id
     */
    private Long id;

    /**
     * 昵称
     */
    private String operatorName;

    /**
     * 数据权限类型
     */
    private Byte dataType;

    /**
     * 用户所属部门编号
     */
    private String deptCode;

    /**
     * 用户所拥有权限部门编号
     */
    private List<String> deptList;

    /**
     * 权限标示符
     */
    private List<String> authList;
}
