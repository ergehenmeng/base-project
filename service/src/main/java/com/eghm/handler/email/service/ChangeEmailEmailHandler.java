package com.eghm.handler.email.service;

import com.eghm.handler.email.AuthCodeEmailHandler;
import org.springframework.stereotype.Component;

/**
 * 更换邮箱时,向新邮箱发送的验证码 默认由父类实现
 *
 * @author 殿小二
 * @since 2020/9/3
 */
@Component("changeEmailHandler")
public class ChangeEmailEmailHandler extends AuthCodeEmailHandler {
}
