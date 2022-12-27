package com.eghm.model.dto.ext;

import lombok.Data;

import java.util.List;

/**
 * 管理后台登录人信息
 * @author 殿小二
 * @date 2020/8/28
 */
@Data
public class JwtManage {

    /**
     * 用户id
     */
    private Long id;

    /**
     * 1: 平台用户 2: 商户用户
     */
    private Integer userType;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 数据权限类型(平台用户专属)
     */
    private Byte dataType;

    /**
     * 用户所属部门编号(平台用户专属)
     */
    private String deptCode;

    /**
     * 用户所拥有权限部门编号(平台用户专属)
     */
    private List<String> deptList;

    /**
     * 权限标示符
     */
    private List<String> authList;
}
