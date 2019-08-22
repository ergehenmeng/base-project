package com.fanyin.model.dto.business.banner;

import com.fanyin.model.ext.PageQuery;
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
public class BannerQueryRequest extends PageQuery {

    /**
     * 轮播图类型
     */
    private Byte classify;

    /**
     * 客户端类型
     */
    private Byte clientType;

    /**
     * 播放时间
     */
    private Date middleTime;
}
