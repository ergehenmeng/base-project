package com.fanyin.model.dto.business.notice;

import com.fanyin.model.ext.PagingQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author 二哥很猛
 * @date 2018/11/20 19:34
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class NoticeQueryRequest extends PagingQuery implements Serializable {

    private static final long serialVersionUID = -6968777991245814063L;


    /**
     * 公告类型
     */
    private Byte classify;

}
