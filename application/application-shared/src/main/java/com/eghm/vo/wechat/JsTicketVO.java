package com.eghm.vo.wechat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2024/4/1
 */

@Data
public class JsTicketVO {

    @Schema(description = "签名")
    private String signature;

    @Schema(description = "时间戳")
    private Long timestamp;

    @Schema(description = "随机字符串")
    private String nonceStr;

    @Schema(description = "公众号appId")
    private String appId;
}
