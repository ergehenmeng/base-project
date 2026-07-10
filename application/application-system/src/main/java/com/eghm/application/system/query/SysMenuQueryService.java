package com.eghm.application.system.query;

import com.eghm.application.shared.dto.ext.Page;
import com.eghm.application.shared.dto.sys.menu.MenuQueryRequest;
import com.eghm.application.shared.utils.StringUtil;
import com.eghm.application.shared.utils.TreeUtil;
import com.eghm.application.shared.vo.sys.menu.MenuFullResponse;
import com.eghm.application.shared.vo.sys.menu.MenuResponse;
import com.eghm.application.shared.vo.sys.menu.MenuTreeResponse;
import com.eghm.domain.system.model.SysMenu;

import java.util.Comparator;
import java.util.List;

/**
 * 菜单查询服务
 *
 * @author 二哥很猛
 */
public interface SysMenuQueryService {

    String ROOT = SysMenu.ROOT;

    Comparator<MenuTreeResponse> MENU_TREE_COMPARATOR = Comparator.comparing(MenuTreeResponse::getSort);

    List<MenuTreeResponse> getLeftList();

    Page<MenuResponse> getByPage(MenuQueryRequest request);

    List<MenuTreeResponse> getMenuList(Long userId, Integer grade);

    List<MenuTreeResponse> getSystemMenuList(Integer grade);

    List<MenuTreeResponse> getAll(Integer displayState);

    List<MenuFullResponse> getList(MenuQueryRequest request);

    List<SysMenu> listEnabledButtons();

    default MenuTreeResponse tree() {
        List<MenuTreeResponse> responseList = this.getLeftList();
        List<MenuTreeResponse> treeBin = TreeUtil.tree(responseList, ROOT, MenuTreeResponse::getId, MenuTreeResponse::getPid, MenuTreeResponse::setChildren, MENU_TREE_COMPARATOR);
        MenuTreeResponse response = new MenuTreeResponse();
        response.setChildren(treeBin);
        response.setId(ROOT);
        response.setTitle("系统菜单");
        return response;
    }

    default List<MenuTreeResponse> listUserLeftMenus(Long userId) {
        List<MenuTreeResponse> list = this.getMenuList(userId, 1);
        return TreeUtil.tree(list, ROOT, MenuTreeResponse::getId, MenuTreeResponse::getPid, MenuTreeResponse::setChildren, MENU_TREE_COMPARATOR);
    }

    default List<MenuTreeResponse> listAdminLeftMenus() {
        List<MenuTreeResponse> list = this.getSystemMenuList(1);
        return TreeUtil.tree(list, ROOT, MenuTreeResponse::getId, MenuTreeResponse::getPid, MenuTreeResponse::setChildren, MENU_TREE_COMPARATOR);
    }

    default List<MenuTreeResponse> listSystemMenus(Integer displayState) {
        List<MenuTreeResponse> responseList = this.getAll(displayState);
        return TreeUtil.tree(responseList, ROOT, MenuTreeResponse::getId, MenuTreeResponse::getPid, MenuTreeResponse::setChildren, MENU_TREE_COMPARATOR);
    }

    default List<MenuFullResponse> listFullMenus(MenuQueryRequest request) {
        if (StringUtil.isNotBlank(request.getQueryName())) {
            request.setPid(null);
        }
        return this.getList(request);
    }

    default List<String> listUserPermCodes(Long userId) {
        List<MenuTreeResponse> menuList = this.getMenuList(userId, 2);
        return menuList.stream().map(MenuTreeResponse::getCode).toList();
    }

    default List<String> listAdminPermCodes() {
        List<MenuTreeResponse> menuList = this.getSystemMenuList(2);
        return menuList.stream().map(MenuTreeResponse::getCode).toList();
    }
}
