package com.eghm.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 */
@Data
@TableName("notice_template")
public class NoticeTemplatePO {
    /** id主键 */
    private Long id;

    /** 消息模板code */
    private String code;

    /** 标题 */
    private String title;

    /** 通知内容 */
    private String content;

    /** 备注信息 */
    private String remark;

    /** 更新时间 */
    private LocalDateTime updateTime;

}

