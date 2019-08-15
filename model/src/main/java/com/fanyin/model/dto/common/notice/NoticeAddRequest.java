package com.fanyin.model.dto.common.notice;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 二哥很猛
 * @date 2018/11/20 19:15
 */
@Data
public class NoticeAddRequest implements Serializable {

    private static final long serialVersionUID = 3360468576576094581L;

    /**
     * 公告标题
     */
    private String title;

    /**
     * 公告类型(数据字典表system_notice_type)
     */
    private Byte classify;

    /**
     * 公告内容(富文本)
     */
    private String content;
}
