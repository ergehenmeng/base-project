package com.eghm.model.dto.ext;

import com.eghm.common.enums.Channel;
import lombok.Data;

import java.util.List;

/**
 * @author 殿小二
 * @date 2020/8/28
 */
@Data
public class JwtToken {

    /**
     * 用户id
     */
    private Long id;

    /**
     * 登陆渠道
     */
    private Channel channel;

    /**
     * 权限标示符
     */
    private List<String> authList;
}
