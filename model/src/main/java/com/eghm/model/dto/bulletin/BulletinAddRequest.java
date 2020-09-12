package com.eghm.model.dto.bulletin;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 二哥很猛
 * @date 2018/11/20 19:15
 */
@Data
public class BulletinAddRequest implements Serializable {

    private static final long serialVersionUID = 3360468576576094581L;

    /**
     * 公告标题
     */
    private String title;

    /**
     * 公告类型(数据字典表sys_bulletin_type)
     */
    private Byte classify;

    /**
     * 公告内容(富文本)
     */
    private String content;

    /**
     * 原始内容
     */
    private String originalContent;
}
