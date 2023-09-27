package com.eghm.web.impl;

import com.eghm.mapper.SysMenuMapper;
import com.eghm.model.SysMenu;
import com.eghm.service.business.MybatisService;
import com.eghm.utils.StringUtil;
import com.eghm.web.BaseTest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2023/7/13
 */
class MybatisTest extends BaseTest {

    @Autowired
    private MybatisService mybatisService;

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Test
    void test() {
        mybatisService.test();
    }

    @Test
    void initCode() {
        List<SysMenu> selectList = sysMenuMapper.selectList(null);
        for (SysMenu menu : selectList) {
            menu.setCode(StringUtil.encryptNumber(Long.parseLong(menu.getId())));
            sysMenuMapper.updateById(menu);
        }
    }
}
