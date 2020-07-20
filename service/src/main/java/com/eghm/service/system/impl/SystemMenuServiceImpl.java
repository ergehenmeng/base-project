package com.eghm.service.system.impl;

import com.eghm.common.enums.ErrorCode;
import com.eghm.common.exception.BusinessException;
import com.eghm.dao.mapper.system.SystemMenuMapper;
import com.eghm.dao.model.system.SystemMenu;
import com.eghm.model.dto.system.menu.MenuAddRequest;
import com.eghm.model.dto.system.menu.MenuEditRequest;
import com.eghm.service.system.SystemMenuService;
import com.eghm.utils.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author 二哥很猛
 * @date 2018/1/26 16:15
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
public class SystemMenuServiceImpl implements SystemMenuService {

    private SystemMenuMapper systemMenuMapper;

    @Autowired
    public void setSystemMenuMapper(SystemMenuMapper systemMenuMapper) {
        this.systemMenuMapper = systemMenuMapper;
    }

    private final Comparator<SystemMenu> comparator = Comparator.comparing(SystemMenu::getSort);

    @Override
    @Transactional(readOnly = true, rollbackFor = RuntimeException.class)
    public List<SystemMenu> getMenuList(Integer operatorId) {
        List<SystemMenu> list = systemMenuMapper.getMenuList(operatorId);
        List<SystemMenu> parentList = new ArrayList<>();

        for (SystemMenu parent : list) {
            if (parent.getPid() == 0) {
                setChild(parent, list);
                parentList.add(parent);
            }
        }
        parentList.sort(comparator);
        return parentList;
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = RuntimeException.class)
    public List<SystemMenu> getButtonList(Integer operatorId) {
        return systemMenuMapper.getButtonList(operatorId);
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = RuntimeException.class)
    public List<SystemMenu> getList(Integer operatorId) {
        return systemMenuMapper.getList(operatorId);
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = RuntimeException.class)
    public SystemMenu getMenuById(Integer id) {
        return systemMenuMapper.selectByPrimaryKey(id);
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = RuntimeException.class)
    public List<SystemMenu> getAllList() {
        return systemMenuMapper.getAllList();
    }

    @Override
    public void addMenu(MenuAddRequest request) {
        SystemMenu copy = DataUtil.copy(request, SystemMenu.class);
        try {
            systemMenuMapper.insertSelective(copy);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.MENU_NID_ERROR);
        }
    }


    @Override
    public void updateMenu(MenuEditRequest request) {

        SystemMenu copy = DataUtil.copy(request, SystemMenu.class);
        int index;
        try {
            index = systemMenuMapper.updateByPrimaryKeySelective(copy);
        } catch (Exception e) {
            //唯一索引会导致异常
            throw new BusinessException(ErrorCode.MENU_NID_ERROR);
        }
        if (index != 1) {
            throw new BusinessException(ErrorCode.UPDATE_MENU_ERROR);
        }
    }

    @Override
    public void deleteMenu(Integer id) {
        systemMenuMapper.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = RuntimeException.class)
    public List<GrantedAuthority> getAuthorityByOperatorId(Integer operator) {
        List<SystemMenu> list = systemMenuMapper.getList(operator);
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (!CollectionUtils.isEmpty(list)) {
            for (SystemMenu menu : list) {
                GrantedAuthority authority = new SimpleGrantedAuthority(menu.getNid());
                authorities.add(authority);
            }
        }
        return authorities;
    }

    /**
     * 设置子菜单列表
     *
     * @param parent 当前菜单
     * @param list   所有用户可操作的菜单
     */
    private void setChild(SystemMenu parent, List<SystemMenu> list) {
        List<SystemMenu> childList = getChild(parent.getId(), list);
        if (!childList.isEmpty()) {
            for (SystemMenu child : childList) {
                setChild(child, list);
            }
            childList.sort(comparator);
            parent.setSubList(childList);
        }
    }

    /**
     * 根据ID查看是否存在子元素
     *
     * @param id   相当于子元素的pid
     * @param list 所有菜单列表
     * @return 子菜单列表
     */
    private List<SystemMenu> getChild(int id, List<SystemMenu> list) {
        List<SystemMenu> childList = new ArrayList<>();
        for (SystemMenu menu : list) {
            if (menu.getPid() == id) {
                childList.add(menu);
            }
        }
        return childList;
    }
}
