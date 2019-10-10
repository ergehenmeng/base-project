package com.fanyin.model.dto.business.feedback;

import com.fanyin.model.ext.PagingQuery;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 二哥很猛
 * @date 2019/8/28 13:50
 */
@Setter
@Getter
@EqualsAndHashCode(callSuper = true)
public class FeedbackQueryRequest extends PagingQuery {

    private static final long serialVersionUID = 1054408796616316094L;

    /**
     * 分类
     */
    private Byte classify;

    /**
     * 状态 0:待解决 1:已解决
     */
    private Byte state;

}
