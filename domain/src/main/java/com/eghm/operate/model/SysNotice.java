package com.eghm.operate.model;

import com.eghm.common.model.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统公告
 *
 * @author 二哥很猛
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysNotice extends BaseEntity {

    /**
     * 未发布
     */
    public static final int STATE_0 = 0;

    /**
     * 已发布
     */
    public static final int STATE_1 = 1;

    /** 公告标题 */
    private String title;

    /** 公告类型(数据字典表notice_type) */
    private Integer noticeType;

    /** 封面图片 */
    private String coverUrl;

    /** 公告内容(富文本) */
    private String content;

    /** 是否发布 0:未发布 1:已发布 */
    private Integer state;

}
