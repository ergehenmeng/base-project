package com.fanyin.model.system;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 角色
 * @author 二哥很猛
 */
@Data
public class SystemRole implements Serializable {
    private static final long serialVersionUID = 584470701709277422L;
    /**
     * 主键<br>
     * 表 : system_role<br>
     * 对应字段 : id<br>
     */
    private Integer id;

    /**
     * 角色名称<br>
     * 表 : system_role<br>
     * 对应字段 : role_name<br>
     */
    private String roleName;

    /**
     * 角色类型<br>
     * 表 : system_role<br>
     * 对应字段 : role_type<br>
     */
    private String roleType;

    /**
     * 添加时间<br>
     * 表 : system_role<br>
     * 对应字段 : add_time<br>
     */
    private Date addTime;

    /**
     * 更新时间<br>
     * 表 : system_role<br>
     * 对应字段 : update_time<br>
     */
    private Date updateTime;

    /**
     * 删除状态:0:正常,1:已删除<br>
     * 表 : system_role<br>
     * 对应字段 : deleted<br>
     */
    private Boolean deleted;

    /**
     * 备注信息<br>
     * 表 : system_role<br>
     * 对应字段 : remark<br>
     */
    private String remark;


}