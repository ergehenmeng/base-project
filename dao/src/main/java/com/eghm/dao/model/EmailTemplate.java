package com.eghm.dao.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 二哥很猛
 */
@Getter
@Setter
@ToString(callSuper = true)
public class EmailTemplate extends BaseEntity {

    /**
     * 模板唯一编码<br>
     * 表 : email_template<br>
     * 对应字段 : nid<br>
     */
    private String nid;

    /**
     * 模板标题<br>
     * 表 : email_template<br>
     * 对应字段 : title<br>
     */
    private String title;

    /**
     * 模板内容<br>
     * 表 : email_template<br>
     * 对应字段 : content<br>
     */
    private String content;

    /**
     * 备注信息<br>
     * 表 : email_template<br>
     * 对应字段 : remark<br>
     */
    private String remark;

}