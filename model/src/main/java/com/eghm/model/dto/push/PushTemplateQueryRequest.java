package com.eghm.model.dto.push;

import com.eghm.model.dto.ext.PagingQuery;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 二哥很猛
 * @date 2019/8/29 16:20
 */
@Getter
@Setter
public class PushTemplateQueryRequest extends PagingQuery {

    private static final long serialVersionUID = 2652554259813794315L;

    /**
     * 状态 0:关闭 1:开启
     */
    private Byte state;

}
