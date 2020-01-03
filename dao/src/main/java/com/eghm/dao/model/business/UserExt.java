package com.eghm.dao.model.business;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户扩展信息
 * @author 二哥很猛
 */
@Data
public class UserExt implements Serializable {

    /**
     * 主键<br>
     * 表 : user_ext<br>
     * 对应字段 : id<br>
     */
    private Integer id;

    /**
     * 投资人用户ID<br>
     * 表 : user_ext<br>
     * 对应字段 : user_id<br>
     */
    private Integer userId;

    /**
     * 真实姓名<br>
     * 表 : user_ext<br>
     * 对应字段 : real_name<br>
     */
    private String realName;

    /**
     * 身份证号码,前6位加密<br>
     * 表 : user_ext<br>
     * 对应字段 : id_card<br>
     */
    private String idCard;

    /**
     * 生日yyyyMMdd<br>
     * 表 : user_ext<br>
     * 对应字段 : birthday<br>
     */
    private String birthday;

    /**
     * 更新时间<br>
     * 表 : user_ext<br>
     * 对应字段 : update_time<br>
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;

}