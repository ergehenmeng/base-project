package com.eghm.dto.member;

import com.eghm.dto.ext.PagingQuery;
import com.eghm.validation.annotation.OptionInt;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 二哥很猛
 * @since 2023/7/19
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MemberQueryRequest extends PagingQuery {

    @ApiModelProperty("状态 false:注销 true:正常")
    private Boolean state;

    @ApiModelProperty("性别 0:未知 1:男 2:女 ")
    @OptionInt(value = {0, 1, 2}, required = false, message = "性别参数非法")
    private Integer sex;

    @ApiModelProperty("注册渠道 PC,ANDROID,IOS,H5,WECHAT,ALIPAY")
    private String channel;
}
