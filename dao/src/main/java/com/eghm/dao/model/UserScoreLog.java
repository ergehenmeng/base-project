package com.eghm.dao.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 二哥很猛
 */
@Data
public class UserScoreLog implements Serializable {
    /**
     * 主键<br>
     * 表 : user_score_log<br>
     * 对应字段 : id<br>
     */
    private Long id;

    /**
     * 用户id<br>
     * 表 : user_score_log<br>
     * 对应字段 : user_id<br>
     */
    private Long userId;

    /**
     * 本次收入或支出的积分数<br>
     * 表 : user_score_log<br>
     * 对应字段 : score<br>
     */
    private Integer score;

    /**
     * 积分收入或支出分类<br>
     * 表 : user_score_log<br>
     * 对应字段 : type<br>
     */
    private Byte type;

    /**
     * 备注信息<br>
     * 表 : user_score_log<br>
     * 对应字段 : remark<br>
     */
    private String remark;

    /**
     * 添加时间<br>
     * 表 : user_score_log<br>
     * 对应字段 : add_time<br>
     */
    private Date addTime;

    private static final long serialVersionUID = 1L;

}