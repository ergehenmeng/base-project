package com.eghm.model.dto.dept;

import com.eghm.model.dto.ext.ActionRecord;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 二哥很猛
 * @date 2018/12/14 14:11
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DeptAddRequest extends ActionRecord {

    private static final long serialVersionUID = -4129318805129787627L;

    /**
     * 父编码
     */
    private String parentCode;


    /**
     * 部门名称
     */
    private String title;

    /**
     * 备注信息
     */
    private String remark;

}
