package com.eghm.handler.email.service;

import com.eghm.handler.email.BaseAuthCodeHandler;
import org.springframework.stereotype.Component;

/**
 * 绑定邮箱时发送短信验证码 由父类实现验证码全部逻辑
 * @author 殿小二
 * @date 2020/8/29
 */
@Component("bindEmailHandler")
public class BindEmailHandler extends BaseAuthCodeHandler {
}
