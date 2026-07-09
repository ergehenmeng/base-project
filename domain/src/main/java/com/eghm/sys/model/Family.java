package com.eghm.sys.model;

import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
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
@EqualsAndHashCode(callSuper = false)
public class Family {

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

    /**
     * 设置新节点id
     *
     * @param id 节点id
     */
    public void assignId(String id) {
        this.id = id;
    }

    /**
     * 校验是否允许删除
     *
     * @param hasChildren 是否存在子节点
     */
    public void assertDeletable(boolean hasChildren) {
        if (hasChildren) {
            throw new BusinessException(ErrorCode.FAMILY_NEXT_ERROR);
        }
    }

    public void initialize(String pid, String name, LocalDate birthday) {
        this.pid = pid;
        this.name = name;
        this.birthday = birthday;
        this.state = false;
        this.createTime = LocalDateTime.now();
        this.deleted = false;
    }

    public void changeName(String name) {
        this.name = name;
        this.updateTime = LocalDateTime.now();
    }

    public void changeBirthday(LocalDate birthday) {
        this.birthday = birthday;
        this.updateTime = LocalDateTime.now();
    }

    public boolean isRoot() {
        return "0".equals(this.pid);
    }
}
