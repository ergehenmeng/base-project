package com.eghm.business.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 */
@Data
public class MemberScoreLog {
    /** id主键 */
    private Long id;

    /** 添加时间 */
    private LocalDateTime createTime;

    /** 用户id */
    private Long memberId;

    /** 本次收入或支出的积分数 */
    private Integer score;

    /** 变更后的积分余额 */
    private Integer surplusScore;

    /** 积分收入或支出分类 */
    private Integer type;

    /** 备注信息 */
    private String remark;

}
