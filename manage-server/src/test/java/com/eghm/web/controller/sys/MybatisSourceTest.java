package com.eghm.web.controller.sys;

import com.eghm.mapper.MybatisSourceMapper;
import com.eghm.model.MybatisRoleMenu;
import com.eghm.model.SysMenu;
import com.eghm.model.User;
import com.eghm.handler.chain.HandlerChain;
import com.eghm.handler.chain.MessageData;
import com.eghm.handler.chain.annotation.HandlerEnum;
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

    @Autowired
    private HandlerChain handlerChain;

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


    @Test
    public void execute() {
        MessageData data = new MessageData();
        data.setUser(new User());
        handlerChain.execute(data, HandlerEnum.REGISTER);
    }
}

