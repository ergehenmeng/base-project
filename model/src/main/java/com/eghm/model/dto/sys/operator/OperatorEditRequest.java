package com.eghm.model.dto.sys.operator;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 二哥很猛
 * @date 2018/11/30 16:37
 */
@Data
public class OperatorEditRequest implements Serializable {
    private static final long serialVersionUID = 2695362882925858038L;

    /**
     * 主键
     */
    private Integer id;

    /**
     * 用户姓名
     */
    private String operatorName;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 角色列表
     */
    private String roleIds;

    /**
     * 备注信息
     */
    private String remark;
}
