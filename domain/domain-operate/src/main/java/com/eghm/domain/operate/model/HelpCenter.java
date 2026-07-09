package com.eghm.domain.operate.model;

import com.eghm.domain.shared.model.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 二哥很猛
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class HelpCenter extends BaseEntity {

    /** 帮助分类取sys_dict表中help_type字段 */
    private Integer helpType;

    /** 状态 0:不显示 1:显示 */
    private Integer state;

    /** 问 */
    private String ask;

    /** 答 */
    private String answer;

    /** 排序(小<->大) */
    private Integer sort;

    public void enable() {
        this.state = 1;
    }

    public void disable() {
        this.state = 0;
    }

    public boolean isEnabled() {
        return Integer.valueOf(1).equals(this.state);
    }

}
