package com.eghm.service.sys.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.constant.CommonConstant;
import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import com.eghm.utils.StringUtil;
import com.eghm.mapper.SysMenuMapper;
import com.eghm.model.SysMenu;
import com.eghm.dto.menu.MenuAddRequest;
import com.eghm.dto.menu.MenuEditRequest;
import com.eghm.vo.menu.MenuResponse;
import com.eghm.service.sys.SysMenuService;
import com.eghm.service.sys.SysRoleService;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 二哥很猛
 * @date 2018/1/26 16:15
 */
@Service("sysMenuService")
@AllArgsConstructor
@Slf4j
public class SysMenuServiceImpl implements SysMenuService {

    private final SysMenuMapper sysMenuMapper;

    private final Comparator<MenuResponse> comparator = Comparator.comparing(MenuResponse::getSort);
    
    private final SysRoleService sysRoleService;

    /**
     * 步长默认2位数即 10~99
     */
    private static final String STEP = "10";

    /**
     * 同级别最多有90个菜单
     */
    private static final int MAX = 90;


    @Override
    public List<MenuResponse> getLeftMenuList(Long userId) {
        List<MenuResponse> list = sysMenuMapper.getMenuList(userId, 1, false);
        return this.treeBin(list);
    }

    @Override
    public List<MenuResponse> getAdminLeftMenuList() {
        List<MenuResponse> list = sysMenuMapper.getMenuList(null, 1, true);
        return this.treeBin(list);
    }

    @Override
    public List<MenuResponse> getList(Long userId) {
        boolean adminRole = sysRoleService.isAdminRole(userId);
        List<MenuResponse> responseList;
        if (adminRole) {
            // adminHide = true, 部分菜单时商户独有菜单,不对超管进行开放
            responseList = sysMenuMapper.getMenuList(null, null, true);
        } else {
            responseList = sysMenuMapper.getMenuList(userId, null, false);
        }
        return this.treeBin(responseList);
    }
    
    @Override
    public List<MenuResponse> getAuthList(Long userId) {
        boolean adminRole = sysRoleService.isAdminRole(userId);
        List<MenuResponse> responseList;
        if (adminRole) {
            // adminHide = true, 因为超管授权时可以授权所有菜单
            responseList = sysMenuMapper.getMenuList(null, null, false);
        } else {
            responseList = sysMenuMapper.getMenuList(userId, null, false);
        }
        return this.treeBin(responseList);
    }
    
    @Override
    public List<SysMenu> getList() {
        return sysMenuMapper.selectList(null);
    }

    @Override
    public List<SysMenu> getButtonList() {
        LambdaQueryWrapper<SysMenu> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SysMenu::getState, true);
        wrapper.eq(SysMenu::getGrade, 2);
        return sysMenuMapper.selectList(wrapper);
    }

    @Override
    public void create(MenuAddRequest request) {
        SysMenu copy = DataUtil.copy(request, SysMenu.class);
        copy.setId(String.valueOf(this.generateNextId(request.getPid())));
        copy.setCode(StringUtil.encryptNumber(Long.parseLong(copy.getId())));
        sysMenuMapper.insert(copy);
    }

    @Override
    public void update(MenuEditRequest request) {
        SysMenu copy = DataUtil.copy(request, SysMenu.class);
        copy.setCode(StringUtil.encryptNumber(Long.parseLong(copy.getId())));
        sysMenuMapper.updateById(copy);
    }

    @Override
    public void delete(String id) {
        sysMenuMapper.deleteById(id);
    }

    @Override
    public void updateState(String id, Integer state) {
        LambdaUpdateWrapper<SysMenu> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(SysMenu::getId, id);
        wrapper.set(SysMenu::getState, state);
        sysMenuMapper.update(null, wrapper);
    }

    @Override
    public List<String> getPermCode(Long user) {
        List<MenuResponse> menuList = sysMenuMapper.getMenuList(user, 2, false);
        return menuList.stream().map(MenuResponse::getCode).collect(Collectors.toList());
    }

    @Override
    public List<String> getAdminPermCode() {
        List<MenuResponse> menuList = sysMenuMapper.getMenuList(null, 2, true);
        return menuList.stream().map(MenuResponse::getCode).collect(Collectors.toList());
    }

    /**
     * 生成pid子菜单中下一个最大id
     * @param pid pid,不能为零
     * @return 最大id
     */
    private Long generateNextId(String pid) {
        String maxId = sysMenuMapper.getMaxId(pid);
        // 空表示当前菜单没有子菜单,直接生成第一个子菜单
        if (StrUtil.isBlank(maxId)) {
            return Long.parseLong(pid + STEP);
        }
        // 如果最后三位是99这表示,已经最大了,再+1会进位,因此不能超过99
        String lastMember = maxId.substring(maxId.length() - 2);
        if (Integer.parseInt(lastMember) >= MAX) {
            log.error("统计菜单不能超过90 [{}] [{}]", pid, maxId);
            throw new BusinessException(ErrorCode.MENU_MAX_ERROR);
        }
        // 最大子菜单+1即可
        return Long.parseLong(maxId) + 1;
    }


    /**
     * 设置子菜单列表
     * 注意:此方法有一定性能问题, 菜单被匹配后并未从list移除,会导致更多迭代次数
     * @param parent 当前菜单
     * @param list   所有用户可操作的菜单
     */
    private void setChild(MenuResponse parent, List<MenuResponse> list) {
        List<MenuResponse> childList = list.stream()
                .filter(item -> parent.getId().equals(item.getPid()))
                .peek(item -> setChild(item, list))
                .sorted(comparator).collect(Collectors.toList());
        parent.setChildren(childList);
    }

    /**
     * 将菜单列表树化
     * @param menuList 菜单列表
     * @return 菜单列表 树状结构
     */
    private List<MenuResponse> treeBin(List<MenuResponse> menuList) {
        return menuList.stream()
                .filter(parent -> CommonConstant.ROOT == parent.getPid())
                .peek(parent -> setChild(parent, menuList))
                .sorted(comparator).collect(Collectors.toList());
    }

}
