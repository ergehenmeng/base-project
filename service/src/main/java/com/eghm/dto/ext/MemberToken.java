package com.eghm.dto.ext;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * token令牌
 *
 * @author 二哥很猛
 * @since 2018/8/14 17:37
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberToken {

    @ApiModelProperty("token 保持登陆信息")
    private String token;

    @ApiModelProperty("刷新token 不序列号保存到redis中")
    private String refreshToken;

    @ApiModelProperty("用户id")
    private Long memberId;

    @ApiModelProperty("登陆来源 ANDROID IOS")
    private String channel;

}
