package com.eghm.model.ext;

import com.eghm.common.enums.Channel;
import lombok.Data;

/**
 * @author 殿小二
 * @date 2020/8/28
 */
@Data
public class JwtToken {

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 登陆渠道
     */
    private Channel channel;
}
