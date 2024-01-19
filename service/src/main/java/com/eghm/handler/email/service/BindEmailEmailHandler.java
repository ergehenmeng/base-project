package com.eghm.handler.email.service;

import com.eghm.handler.email.AuthCodeEmailHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 绑定邮箱时发送短信验证码 由父类实现验证码全部逻辑
 *
 * @author 殿小二
 * @since 2020/8/29
 */
@Component("bindEmailHandler")
@AllArgsConstructor
public class BindEmailEmailHandler extends AuthCodeEmailHandler {
}
