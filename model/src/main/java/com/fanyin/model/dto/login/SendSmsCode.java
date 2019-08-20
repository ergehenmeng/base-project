package com.fanyin.model.dto.login;

import com.fanyin.model.validation.annotation.Mobile;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 二哥很猛
 * @date 2019/8/20 10:50
 */
@Data
public class SendSmsCode implements Serializable {

    /**
     * 手机号
     */
    @Mobile(required = true)
    private String mobile;
}
