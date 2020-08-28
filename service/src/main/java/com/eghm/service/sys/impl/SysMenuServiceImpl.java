package com.eghm.service.sys.impl;

import com.eghm.common.enums.ErrorCode;
import com.eghm.common.exception.BusinessException;
import com.eghm.dao.mapper.system.SysMenuMapper;
import com.eghm.dao.model.system.SysMenu;
import com.eghm.model.dto.sys.menu.MenuAddRequest;
import com.eghm.model.dto.sys.menu.MenuEditRequest;
import com.eghm.service.sys.SysMenuService;
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
@Service("sysMenuService")
@Transactional(rollbackFor = RuntimeException.class)
public class SysMenuServiceImpl implements SysMenuService {

    private SysMenuMapper sysMenuMapper;

    @Autowired
    public void setSysMenuMapper(SysMenuMapper sysMenuMapper) {
        this.sysMenuMapper = sysMenuMapper;
    }

    private final Comparator<SysMenu> comparator = Comparator.comparing(SysMenu::getSort);

    @Override
    @Transactional(readOnly = true, rollbackFor = RuntimeException.class)
    public List<SysMenu> getMenuList(Integer operatorId) {
        List<SysMenu> list = sysMenuMapper.getMenuList(operatorId);
        List<SysMenu> parentList = new ArrayList<>();

        for (SysMenu parent : list) {
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
    public List<SysMenu> getButtonList(Integer operatorId) {
        return sysMenuMapper.getButtonList(operatorId);
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = RuntimeException.class)
    public List<SysMenu> getList(Integer operatorId) {
        return sysMenuMapper.getList(operatorId);
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = RuntimeException.class)
    public SysMenu getMenuById(Integer id) {
        return sysMenuMapper.selectByPrimaryKey(id);
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = RuntimeException.class)
    public List<SysMenu> getAllList() {
        return sysMenuMapper.getAllList();
    }

    @Override
    public void addMenu(MenuAddRequest request) {
        SysMenu copy = DataUtil.copy(request, SysMenu.class);
        try {
            sysMenuMapper.insertSelective(copy);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.MENU_NID_ERROR);
        }
    }


    @Override
    public void updateMenu(MenuEditRequest request) {

        SysMenu copy = DataUtil.copy(request, SysMenu.class);
        int index;
        try {
            index = sysMenuMapper.updateByPrimaryKeySelective(copy);
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
        sysMenuMapper.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = RuntimeException.class)
    public List<GrantedAuthority> getAuthorityByOperatorId(Integer operator) {
        List<SysMenu> list = sysMenuMapper.getList(operator);
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (!CollectionUtils.isEmpty(list)) {
            for (SysMenu menu : list) {
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
    private void setChild(SysMenu parent, List<SysMenu> list) {
        List<SysMenu> childList = getChild(parent.getId(), list);
        if (!childList.isEmpty()) {
            for (SysMenu child : childList) {
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
    private List<SysMenu> getChild(int id, List<SysMenu> list) {
        List<SysMenu> childList = new ArrayList<>();
        for (SysMenu menu : list) {
            if (menu.getPid() == id) {
                childList.add(menu);
            }
        }
        return childList;
    }
}
