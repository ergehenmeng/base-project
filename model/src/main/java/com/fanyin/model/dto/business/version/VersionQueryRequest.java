package com.fanyin.model.dto.business.version;

import com.fanyin.model.ext.PageQuery;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 二哥很猛
 * @date 2019/8/22 15:12
 */
@Setter
@Getter
public class VersionQueryRequest extends PageQuery {

    private static final long serialVersionUID = 3227181256921372184L;

    /**
     * 上架状态
     */
    private Byte state;

}
