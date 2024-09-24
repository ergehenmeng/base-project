package com.eghm.common.impl;

import com.eghm.common.SendSmsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 默认短信发送实现
 *
 * @author 二哥很猛
 * @since 2019/8/20 17:01
 */
@Slf4j
@Service("sendSmsService")
public class SendSmsServiceImpl implements SendSmsService {

    @Override
    public int sendSms(String mobile, String content) {
        log.info("发送短信, 手机号:[{}] 短信内容:[{}]", mobile, content);
        return 0;
    }

    @Override
    public int sendSms(String mobile, String templateName, Map<String, Object> params) {
        log.info("发送短信, 手机号:[{}] 短信模板:[{}] 参数: [{}]", mobile, templateName, params);
        return 0;
    }
}
