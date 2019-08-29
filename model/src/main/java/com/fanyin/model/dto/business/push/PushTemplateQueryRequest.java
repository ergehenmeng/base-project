package com.fanyin.model.dto.business.push;

import com.fanyin.model.ext.PageQuery;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 二哥很猛
 * @date 2019/8/29 16:20
 */
@Getter
@Setter
public class PushTemplateQueryRequest extends PageQuery{

    private static final long serialVersionUID = 2652554259813794315L;

    /**
     * 状态 0:关闭 1:开启
     */
    private Boolean state;

}
