package com.eghm.dao.model.business;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 二哥很猛
 */
@Data
public class EmailTemplate implements Serializable {
    /**
     * 主键<br>
     * 表 : email_template<br>
     * 对应字段 : id<br>
     */
    private Integer id;

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
     * 更新时间<br>
     * 表 : email_template<br>
     * 对应字段 : update_time<br>
     */
    private Date updateTime;

    /**
     * 备注信息<br>
     * 表 : email_template<br>
     * 对应字段 : remark<br>
     */
    private String remark;

    private static final long serialVersionUID = 1L;

}