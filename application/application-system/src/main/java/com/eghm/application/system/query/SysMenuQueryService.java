package com.eghm.application.system.query;

import com.eghm.application.shared.dto.ext.Page;
import com.eghm.application.shared.dto.sys.menu.MenuQueryRequest;
import com.eghm.application.shared.vo.sys.menu.MenuFullResponse;
import com.eghm.application.shared.vo.sys.menu.MenuResponse;
import com.eghm.application.shared.vo.sys.menu.MenuTreeResponse;

import java.util.List;

/**
 * 菜单查询服务
 *
 * @author 二哥很猛
 */
public interface SysMenuQueryService {

    List<MenuTreeResponse> getLeftList();

    Page<MenuResponse> getByPage(MenuQueryRequest request);

    List<MenuTreeResponse> getMenuList(Long userId, Integer grade);

    List<MenuTreeResponse> getSystemMenuList(Integer grade);

    List<MenuTreeResponse> getAll(Integer displayState);

    List<MenuFullResponse> getList(MenuQueryRequest request);
}
