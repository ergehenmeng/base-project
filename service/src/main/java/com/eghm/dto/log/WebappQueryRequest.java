package com.eghm.dto.log;

import com.eghm.dto.ext.PagingQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 二哥很猛
 * @since 2023/7/24
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class WebappQueryRequest extends PagingQuery {

    @ApiModelProperty("注册渠道 PC,ANDROID,IOS,H5,WECHAT,ALIPAY")
    private String channel;

    @ApiModelProperty("访问ip")
    private String ip;
}
