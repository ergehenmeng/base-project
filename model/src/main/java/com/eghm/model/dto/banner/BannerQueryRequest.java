package com.eghm.model.dto.banner;

import com.eghm.model.dto.ext.PagingQuery;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author 二哥很猛
 * @date 2019/8/22 11:25
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class BannerQueryRequest extends PagingQuery {

    private static final long serialVersionUID = -7234026470157744373L;

    /**
     * 轮播图类型
     */
    private Byte classify;

    /**
     * 客户端类型
     */
    private String clientType;

    /**
     * 播放时间
     */
    private Date middleTime;
}
