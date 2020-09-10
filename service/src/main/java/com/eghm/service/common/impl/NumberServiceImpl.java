package com.eghm.service.common.impl;

import com.eghm.service.common.KeyGenerator;
import com.eghm.service.common.NumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * 常用服务类
 *
 * @author 二哥很猛
 * @date 2018/1/18 14:17
 */
@Service("numberService")
public class NumberServiceImpl implements NumberService {

    private KeyGenerator keyGenerator;

    @Autowired
    public void setKeyGenerator(KeyGenerator keyGenerator) {
        this.keyGenerator = keyGenerator;
    }

    @Override
    public String getNumber(String prefix) {
        return prefix + this.getNumber();
    }

    @Override
    public String getNumber() {
        return DateTimeFormatter.ofPattern("yyyyMMdd").format(LocalDate.now()) + keyGenerator.generateKey();
    }
}
