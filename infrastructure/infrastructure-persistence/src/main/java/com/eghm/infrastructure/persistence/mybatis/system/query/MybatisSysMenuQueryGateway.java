package com.eghm.infrastructure.persistence.mybatis.system.query;

import com.eghm.dto.ext.Page;
import com.eghm.infrastructure.persistence.mybatis.query.MybatisPageUtil;
import com.eghm.dto.sys.menu.MenuQueryRequest;
import com.eghm.infrastructure.persistence.mybatis.mapper.SysMenuMapper;
import com.eghm.application.system.service.SysMenuQueryGateway;
import com.eghm.vo.sys.menu.MenuFullResponse;
import com.eghm.vo.sys.menu.MenuResponse;
import com.eghm.vo.sys.menu.MenuTreeResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * MyBatis菜单查询网关实现
 *
 * @author 二哥很猛
 */
@Repository
@AllArgsConstructor
public class MybatisSysMenuQueryGateway implements SysMenuQueryGateway {

    private final SysMenuMapper sysMenuMapper;

    @Override
    public List<MenuTreeResponse> getLeftList() {
        return sysMenuMapper.getLeftList();
    }

    @Override
    public Page<MenuResponse> getByPage(MenuQueryRequest request) {
        return MybatisPageUtil.fromMybatis(sysMenuMapper.getByPage(MybatisPageUtil.toMybatis(request.createPage()), request));
    }

    @Override
    public List<MenuTreeResponse> getMenuList(Long userId, Integer grade) {
        return sysMenuMapper.getMenuList(userId, grade);
    }

    @Override
    public List<MenuTreeResponse> getSystemMenuList(Integer grade) {
        return sysMenuMapper.getSystemMenuList(grade);
    }

    @Override
    public List<MenuTreeResponse> getAll(Integer displayState) {
        return sysMenuMapper.getAll(displayState);
    }

    @Override
    public List<MenuFullResponse> getList(MenuQueryRequest request) {
        return sysMenuMapper.getList(request);
    }
}

