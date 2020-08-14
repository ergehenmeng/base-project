package com.eghm.model.dto.business.notice;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 二哥很猛
 * @date 2018/11/20 19:17
 */
@Data
public class NoticeEditRequest implements Serializable {

    private static final long serialVersionUID = 9017621972387136443L;

    /**
     * 主键
     */
    private Integer id;

    /**
     * 公告标题
     */
    private String title;

    /**
     * 公告类型(数据字典表sys_notice_type)
     */
    private Byte classify;

    /**
     * 公告内容(富文本)
     */
    private String content;

    /**
     * 原始内容 不含html标签
     */
    private String originalContent;

}
