package com.eghm.dto.ext;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * token令牌
 * 序列号时命名尽量短,减少redis内存占用
 *
 * @author 二哥很猛
 * @since 2018/8/14 17:37
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberToken {

    @Schema(description = "token")
    @JsonProperty("t")
    private String token;

    @Schema(description = "刷新token")
    @JsonProperty("rt")
    private String refreshToken;

    @Schema(description = "会员id")
    @JsonProperty("mid")
    private Long memberId;

    @Schema(description = "登陆来源 ANDROID IOS")
    @JsonProperty("c")
    private String channel;

}
