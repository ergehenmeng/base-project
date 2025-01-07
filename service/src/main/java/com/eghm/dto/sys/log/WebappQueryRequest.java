package com.eghm.dto.sys.log;

import com.eghm.dto.ext.PagingQuery;
import com.eghm.validation.annotation.OptionString;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 二哥很猛
 * @since 2023/7/24
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class WebappQueryRequest extends PagingQuery {

    @Schema(description = "访问渠道 PC ANDROID IOS H5 WECHAT")
    @OptionString(value = {"PC", "ANDROID", "IOS", "H5", "WECHAT"}, required = false, message = "访问渠道错误")
    private String channel;
}
