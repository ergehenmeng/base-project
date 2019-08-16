package com.fanyin.service.common.impl;

import com.fanyin.service.common.SmsSendService;
import com.fanyin.service.system.SmsLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @date 2019/8/16 18:46
 */
@Service("smsSendService")
public class SmsSendServiceImpl implements SmsSendService {



    @Autowired
    private SmsLogService smsLogService;


}
