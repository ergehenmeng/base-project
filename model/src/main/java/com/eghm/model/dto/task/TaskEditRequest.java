package com.eghm.model.dto.task;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 二哥很猛
 * @date 2019/9/12 18:00
 */
@Data
public class TaskEditRequest implements Serializable {

    private static final long serialVersionUID = -2227192870653756523L;

    /**
     * 主键
     */
    private Integer id;

    /**
     * 表达式
     */
    private String cronExpression;

    /**
     * 开启获取关闭
     */
    private Byte state;

    /**
     * 备注信息
     */
    private String remark;
}
