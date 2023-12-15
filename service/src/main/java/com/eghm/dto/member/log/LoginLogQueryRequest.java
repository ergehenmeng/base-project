package com.eghm.dto.member.log;

import com.eghm.dto.ext.PagingQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 二哥很猛
 * @since 2023/12/15
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class LoginLogQueryRequest extends PagingQuery {

    @ApiModelProperty("会员id")
    private Long memberId;

    @ApiModelProperty("登录渠道")
    private String channel;
}
