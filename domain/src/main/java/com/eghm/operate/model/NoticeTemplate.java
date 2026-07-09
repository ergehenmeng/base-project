package com.eghm.operate.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 */
@Data
public class NoticeTemplate {
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

    public void initialize(String code, String title, String content, String remark) {
        this.code = code;
        this.title = title;
        this.content = content;
        this.remark = remark;
    }

    public void changeContent(String content) {
        this.content = content;
        this.updateTime = LocalDateTime.now();
    }

}
