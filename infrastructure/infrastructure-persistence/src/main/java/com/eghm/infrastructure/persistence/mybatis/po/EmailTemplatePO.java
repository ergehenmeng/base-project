package com.eghm.infrastructure.persistence.mybatis.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 */
@Data
@TableName("email_template")
public class EmailTemplatePO {
    /** id主键 */
    private Long id;

    /** 模板唯一编码 */
    private String nid;

    /** 模板标题 */
    private String title;

    /** 模板内容 */
    private String content;

    /** 备注信息 */
    private String remark;

    /** 更新时间 */
    private LocalDateTime updateTime;
}

