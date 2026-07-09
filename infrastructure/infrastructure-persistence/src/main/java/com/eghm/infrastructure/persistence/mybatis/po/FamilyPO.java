package com.eghm.infrastructure.persistence.mybatis.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 *
 * 族谱信息表
 * @since 2025-12-15
 */
@Data
@TableName("family")
@EqualsAndHashCode(callSuper = false)
public class FamilyPO {

    /** 主键id */
    private String id;

    /** 父节点 */
    private String pid;

    /** 姓名 */
    private String name;

    /** 出生日期 */
    private LocalDate birthday;

    /** 状态 0: 未绝户 1: 已绝户 */
    private Boolean state;

    /** 备注信息 */
    private String remark;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 更新时间 */
    private LocalDateTime updateTime;

    /** 删除状态 0:未删除 1:已删除 */
    private Boolean deleted;

}

