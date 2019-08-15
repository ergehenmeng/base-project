package com.fanyin.dto.system.operator;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 二哥很猛
 * @date 2018/11/26 10:05
 */
@Data
public class PasswordEditRequest implements Serializable {

    private static final long serialVersionUID = 1608188266129161564L;
    /**
     * 旧密码
     */
    private String oldPwd;

    /**
     * 新密码
     */
    private String newPwd;

    /**
     * 用户id
     */
    private Integer operatorId;
}
