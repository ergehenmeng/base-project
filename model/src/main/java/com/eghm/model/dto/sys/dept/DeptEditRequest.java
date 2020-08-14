package com.eghm.model.dto.sys.dept;

import com.eghm.model.ext.ActionRecord;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 二哥很猛
 * @date 2019/8/9 14:59
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DeptEditRequest extends ActionRecord {

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
