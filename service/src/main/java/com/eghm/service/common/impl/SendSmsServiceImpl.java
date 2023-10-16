package com.eghm.service.common.impl;

import com.eghm.service.common.SendSmsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 默认短信发送实现
 *
 * @author 二哥很猛
 * @date 2019/8/20 17:01
 */
@Slf4j
@Service("sendSmsService")
public class SendSmsServiceImpl implements SendSmsService {

    @Override
    public int sendSms(String mobile, String content) {
        log.info("发送短信, 手机号:[{}] 短信内容:[{}]", mobile, content);
        return 0;
    }
}
