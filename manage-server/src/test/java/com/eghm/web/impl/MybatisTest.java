package com.eghm.web.impl;

import com.eghm.service.business.MybatisService;
import com.eghm.web.BaseTest;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author 二哥很猛
 * @since 2023/7/13
 */
public class MybatisTest extends BaseTest {

    @Autowired
    private MybatisService mybatisService;

    @Test
    public void test() {
        mybatisService.test();
    }
}
