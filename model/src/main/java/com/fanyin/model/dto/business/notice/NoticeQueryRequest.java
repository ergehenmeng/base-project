package com.fanyin.model.dto.business.notice;

import com.fanyin.model.ext.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 二哥很猛
 * @date 2018/11/20 19:34
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class NoticeQueryRequest extends PageQuery implements Serializable {

    private static final long serialVersionUID = -6968777991245814063L;

    /**
     * 标题
     */
    private String title;

    /**
     * 公告类型
     */
    private Byte classify;

    /**
     * 查询开始时间
     */
    private Date startTime;

    /**
     * 查询结束时间
     */
    private Date endTime;
}
