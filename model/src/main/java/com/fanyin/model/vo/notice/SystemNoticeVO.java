package com.fanyin.model.vo.notice;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 二哥很猛
 * @date 2019/11/26 17:44
 */
@Data
public class SystemNoticeVO implements Serializable {

    /**
     * 主键
     */
    private Integer id;

    /**
     * 公告标题
     */
    private String title;

    /**
     * 公告类型
     */
    private String classifyName;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 是否发布 0:未发布 1:已发布
     */
    private Byte state;
}
