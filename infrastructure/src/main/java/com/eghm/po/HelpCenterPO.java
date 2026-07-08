package com.eghm.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 二哥很猛
 */
@Data
@TableName("help_center")
@EqualsAndHashCode(callSuper = true)
public class HelpCenterPO extends BaseEntityPO {

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

}


