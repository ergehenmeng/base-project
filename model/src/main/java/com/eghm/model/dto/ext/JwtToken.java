package com.eghm.model.dto.ext;

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
     * 昵称
     */
    private String userName;

    /**
     * 权限标示符
     */
    private List<String> authList;
}
