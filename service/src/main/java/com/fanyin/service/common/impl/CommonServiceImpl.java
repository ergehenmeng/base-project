package com.fanyin.service.common.impl;

import com.fanyin.common.utils.DateUtil;
import com.fanyin.common.utils.Md5Util;
import com.fanyin.service.common.CommonService;
import com.fanyin.service.key.KeyGenerator;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 常用服务类
 *
 * @author 二哥很猛
 * @date 2018/1/18 14:17
 */
@Service("commonService")
@Transactional(rollbackFor = RuntimeException.class, readOnly = true)
public class CommonServiceImpl implements CommonService {

    @Autowired
    private KeyGenerator keyGenerator;

    @Override
    public String encryptPassword(String password, String random) {
        return Md5Util.md5(password + random);
    }

    @Override
    public String getOrderNo() {
        return DateFormatUtils.format(DateUtil.getNow(), "yyyyMMddHHmmss00") + keyGenerator.generateKey();
    }

    @Override
    public String getDepositNo() {
        return DateFormatUtils.format(DateUtil.getNow(), "yyyyMMdd00") + keyGenerator.generateKey();
    }


}
