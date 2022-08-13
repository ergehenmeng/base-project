package com.eghm.model.dto.banner;

import com.eghm.model.dto.ext.PagingQuery;
import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty("轮播图类型")
    private Byte classify;

    @ApiModelProperty("客户端类型 ANDROID IOS")
    private String clientType;

    @ApiModelProperty("播放时间(在该时间段播放)")
    private Date middleTime;
}
