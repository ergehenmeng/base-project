package com.eghm.web.controller.sys;

import com.eghm.dao.mapper.MybatisSourceMapper;
import com.eghm.dao.model.MybatisRoleMenu;
import com.eghm.dao.model.SysMenu;
import com.eghm.web.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author 殿小二
 * @date 2021/2/22
 */
public class MybatisSourceTest extends BaseTest {

    @Autowired
    private MybatisSourceMapper mybatisSourceMapper;

    @Test
    public void queryById() {
        System.out.println(mybatisSourceMapper.getById(13L));
    }

    /**
     * 懒加载 关闭idea debug的toString方法才能看到效果
     */
    @Test
    public void getRoleId() {
        MybatisRoleMenu byRoleId = mybatisSourceMapper.getByRoleId(904L);
        SysMenu menuList = byRoleId.getMenuList();
        // System.out.println(menuList);
    }

    @Test
    public void getDiscriminator() {
        System.out.println(mybatisSourceMapper.getDiscriminator(1L));
    }
}
