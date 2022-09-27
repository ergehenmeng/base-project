package com.eghm.web.controller.sys;

import com.eghm.common.utils.StringUtil;
import com.eghm.dao.mapper.SysMenuMapper;
import com.eghm.dao.model.SysMenu;
import com.eghm.model.dto.dept.DeptAddRequest;
import com.eghm.service.sys.SysDeptService;
import com.eghm.web.BaseTest;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithUserDetails;

import java.util.List;

@Slf4j
public class DeptControllerTest extends BaseTest {

    @Autowired
    private SysDeptService sysDeptService;

    @Autowired
    private SysMenuMapper sysMenuMapper;


    @Test
    public void add() {
        DeptAddRequest request = new DeptAddRequest();
        request.setParentCode("100");
        request.setTitle("我是一级部门第二");
        request.setRemark("我是个备注");
        sysDeptService.create(request);
    }

    @Test
    public void updateCode() {
        List<SysMenu> menuList = sysMenuMapper.selectList(null);
        for (SysMenu menu : menuList) {
            menu.setCode(StringUtil.encryptNumber(menu.getId()));
            log.error("菜单信息 [{}] ", menu);
            sysMenuMapper.updateById(menu);
        }
    }

    @Test
    public void test() {
        System.out.println(sysMenuMapper.getList(1L));
    }

}
