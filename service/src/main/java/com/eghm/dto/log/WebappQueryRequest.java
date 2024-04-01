package com.eghm.dto.log;

import com.eghm.dto.ext.PagingQuery;
import com.eghm.validation.annotation.OptionString;
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

    @ApiModelProperty("注册渠道 PC,ANDROID,IOS,H5,WECHAT")
    @OptionString(value = {"PC", "ANDROID", "IOS", "H5", "WECHAT"}, required = false, message = "注册渠道错误")
    private String channel;

    @ApiModelProperty("访问ip")
    private String ip;

    @ApiModelProperty("请求traceId")
    private String traceId;
}
