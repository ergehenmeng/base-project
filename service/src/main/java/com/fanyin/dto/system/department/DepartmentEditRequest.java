package com.fanyin.dto.system.department;

import com.fanyin.ext.Operation;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 王艳兵
 * @date 2019/8/9 14:59
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DepartmentEditRequest extends Operation {

    /**
     * 主键
     */
    private Integer id;

    /**
     * 标题
     */
    private String title;

    /**
     * 备注信息
     */
    private String remark;
}
