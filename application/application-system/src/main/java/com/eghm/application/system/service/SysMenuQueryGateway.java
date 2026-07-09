package com.eghm.application.system.service;

import com.eghm.dto.ext.Page;
import com.eghm.dto.sys.menu.MenuQueryRequest;
import com.eghm.vo.sys.menu.MenuFullResponse;
import com.eghm.vo.sys.menu.MenuResponse;
import com.eghm.vo.sys.menu.MenuTreeResponse;

import java.util.List;

/**
 * 菜单查询网关
 *
 * @author 二哥很猛
 */
public interface SysMenuQueryGateway {

    List<MenuTreeResponse> getLeftList();

    Page<MenuResponse> getByPage(MenuQueryRequest request);

    List<MenuTreeResponse> getMenuList(Long userId, Integer grade);

    List<MenuTreeResponse> getSystemMenuList(Integer grade);

    List<MenuTreeResponse> getAll(Integer displayState);

    List<MenuFullResponse> getList(MenuQueryRequest request);
}
