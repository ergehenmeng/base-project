package com.eghm.service.common.impl;

import cn.hutool.crypto.digest.MD5;
import com.eghm.common.utils.DateUtil;
import com.eghm.service.common.CommonService;
import com.eghm.service.key.KeyGenerator;
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

    private KeyGenerator keyGenerator;

    @Autowired
    public void setKeyGenerator(KeyGenerator keyGenerator) {
        this.keyGenerator = keyGenerator;
    }

    @Override
    public String encryptPassword(String password, String random) {
        return MD5.create().digestHex16(password + random);
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
