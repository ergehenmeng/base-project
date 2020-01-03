package com.fanyin.service.common.impl;

import com.fanyin.service.common.SendSmsService;
import org.springframework.stereotype.Service;

/**
 * 默认短信发送实现
 *
 * @author 二哥很猛
 * @date 2019/8/20 17:01
 */
@Service("sendSmsService")
public class SendSmsServiceImpl implements SendSmsService {

    @Override
    public byte sendSms(String mobile, String content) {
        //TODO 待实现短信
        return 0;
    }
}
