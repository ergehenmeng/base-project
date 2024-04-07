package com.eghm.dto.ext;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * token令牌
 * 序列号时命名尽量短,减少redis内存占用
 * @author 二哥很猛
 * @since 2018/8/14 17:37
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberToken {

    @ApiModelProperty("token")
    @JsonProperty("t")
    private String token;

    @ApiModelProperty("刷新token")
    @JsonProperty("rt")
    private String refreshToken;

    @ApiModelProperty("会员id")
    @JsonProperty("mid")
    private Long memberId;

    @ApiModelProperty("登陆来源 ANDROID IOS")
    @JsonProperty("c")
    private String channel;

}
