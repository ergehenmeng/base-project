package com.eghm.vo.business.merchant;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @since 2024/1/16
 */

@Data
public class MerchantAuthResponse {

    @Schema(description = "授权码base64图片")
    private String authCode;

    @Schema(description = "授权码过期时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime expireTime;
}
