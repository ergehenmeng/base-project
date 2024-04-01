package com.eghm.vo.wechat;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2024/4/1
 */

@Data
public class JsTicketVO {

    @ApiModelProperty("签名")
    private String signature;

    @ApiModelProperty("时间戳")
    private Long timestamp;

    @ApiModelProperty("随机字符串")
    private String nonceStr;

    @ApiModelProperty("公众号appId")
    private String appId;
}
