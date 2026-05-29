package com.eghm.service.sys.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.common.CommonService;
import com.eghm.constants.CommonConstant;
import com.eghm.dto.sys.menu.MenuAddRequest;
import com.eghm.dto.sys.menu.MenuEditRequest;
import com.eghm.dto.sys.menu.MenuQueryRequest;
import com.eghm.enums.DisplayState;
import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.SysMenuMapper;
import com.eghm.model.SysMenu;
import com.eghm.service.sys.SysMenuService;
import com.eghm.utils.DataUtil;
import com.eghm.utils.StringUtil;
import com.eghm.utils.TreeUtil;
import com.eghm.utils.ValidationUtil;
import com.eghm.vo.sys.menu.MenuFullResponse;
import com.eghm.vo.sys.menu.MenuResponse;
import com.eghm.vo.sys.menu.MenuTreeResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * @author 二哥很猛
 * @since 2018/1/26 16:15
 */
@Slf4j
@AllArgsConstructor
@Service("sysMenuService")
public class SysMenuServiceImpl implements SysMenuService {

    /**
     * 根节点
     */
    private static final String ROOT = "0";

    private final SysMenuMapper sysMenuMapper;

    private final CommonService commonService;

    private static final Comparator<MenuTreeResponse> COMPARATOR = Comparator.comparing(MenuTreeResponse::getSort);

    @Override
    public MenuTreeResponse tree() {
        List<MenuTreeResponse> responseList = sysMenuMapper.getLeftList();
        List<MenuTreeResponse> treeBin = TreeUtil.tree(responseList, ROOT, MenuTreeResponse::getId, MenuTreeResponse::getPid, MenuTreeResponse::setChildren, COMPARATOR);
        MenuTreeResponse response = new MenuTreeResponse();
        response.setChildren(treeBin);
        response.setId(ROOT);
        response.setTitle("系统菜单");
        return response;
    }

    @Override
    public Page<MenuResponse> getByPage(MenuQueryRequest request) {
        return sysMenuMapper.getByPage(request.createPage(), request);
    }

    @Override
    public List<MenuTreeResponse> getLeftMenuList(Long userId) {
        List<MenuTreeResponse> list = sysMenuMapper.getMenuList(userId, 1);
        return TreeUtil.tree(list, ROOT, MenuTreeResponse::getId, MenuTreeResponse::getPid, MenuTreeResponse::setChildren, COMPARATOR);
    }

    @Override
    public List<MenuTreeResponse> getAdminLeftMenuList() {
        List<MenuTreeResponse> list = sysMenuMapper.getSystemMenuList(1);
        return TreeUtil.tree(list, ROOT, MenuTreeResponse::getId, MenuTreeResponse::getPid, MenuTreeResponse::setChildren, COMPARATOR);
    }

    @Override
    public List<MenuTreeResponse> getAll(Integer displayState) {
        List<MenuTreeResponse> responseList = sysMenuMapper.getAll(displayState);
        return TreeUtil.tree(responseList, ROOT, MenuTreeResponse::getId, MenuTreeResponse::getPid, MenuTreeResponse::setChildren, COMPARATOR);
    }

    @Override
    public List<MenuFullResponse> getList(MenuQueryRequest request) {
        // 由于是懒加载, 如果有查询条件, 则将pid置为null
        if (StringUtil.isNotBlank(request.getQueryName())) {
            request.setPid(null);
        }
        return sysMenuMapper.getList(request);
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
        ValidationUtil.redoCheck(sysMenuMapper, SysMenu::getTitle, request.getTitle(), wrapper -> wrapper.eq(SysMenu::getPid, request.getPid()), null, SysMenu::getId, ErrorCode.MENU_TITLE_REDO, "菜单名称重复 [{}] [{}]");
        this.checkDisplayState(request.getPid(), request.getDisplayState());
        SysMenu copy = DataUtil.copy(request, SysMenu.class);
        copy.setId(String.valueOf(this.generateNextId(request.getPid())));
        copy.setCode(StringUtil.encryptNumber(Long.parseLong(copy.getId())));
        sysMenuMapper.insert(copy);
    }

    @Override
    public void update(MenuEditRequest request) {
        ValidationUtil.redoCheck(sysMenuMapper, SysMenu::getTitle, request.getTitle(), wrapper -> wrapper.eq(SysMenu::getPid, request.getPid()), request.getId(), SysMenu::getId, ErrorCode.MENU_TITLE_REDO, "菜单名称重复 [{}] [{}]");
        this.checkDisplayState(request.getPid(), request.getDisplayState());
        SysMenu copy = DataUtil.copy(request, SysMenu.class);
        copy.setCode(StringUtil.encryptNumber(Long.parseLong(copy.getId())));
        sysMenuMapper.updateById(copy);
    }

    @Override
    public void delete(String id) {
        sysMenuMapper.deleteById(id);
    }

    @Override
    public void updateState(String id, Boolean state) {
        LambdaUpdateWrapper<SysMenu> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(SysMenu::getId, id);
        wrapper.set(SysMenu::getState, state);
        sysMenuMapper.update(null, wrapper);
    }

    @Override
    public void sortBy(String id, Integer sortBy) {
        LambdaUpdateWrapper<SysMenu> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(SysMenu::getId, id);
        wrapper.set(SysMenu::getSort, sortBy);
        sysMenuMapper.update(null, wrapper);
    }

    @Override
    public List<String> getPermCode(Long userId) {
        List<MenuTreeResponse> menuList = sysMenuMapper.getMenuList(userId, 2);
        return menuList.stream().map(MenuTreeResponse::getCode).toList();
    }

    @Override
    public List<String> getAdminPermCode() {
        List<MenuTreeResponse> menuList = sysMenuMapper.getSystemMenuList(2);
        return menuList.stream().map(MenuTreeResponse::getCode).toList();
    }

    /**
     * 校验节点显示状态是否满足要求,
     * 例如: 父节点是商户显示,子节点只能是商户显示
     * 例如: 父节点是平台显示,子节点只能是平台显示
     * 例如: 父节点是全部显示,子节点可以是任意状态
     *
     * @param pid          父节点
     * @param displayState 当前节点的显示状态
     */
    private void checkDisplayState(String pid, Integer displayState) {
        if (Objects.equals(pid, ROOT)) {
            return;
        }
        SysMenu sysMenu = sysMenuMapper.selectById(pid);
        if (sysMenu == null) {
            log.warn("父菜单节点不存在 [{}]", pid);
            throw new BusinessException(ErrorCode.PID_MENU_NULL);
        }
        if (sysMenu.getDisplayState() != DisplayState.ALL.getValue() && !sysMenu.getDisplayState().equals(displayState)) {
            log.warn("菜单节点显示状态不满足要求 [{}] [{}] [{}]", pid, sysMenu.getDisplayState(), displayState);
            throw new BusinessException(ErrorCode.PID_MENU_STATE);
        }
    }

    /**
     * 生成pid子菜单中下一个最大id
     *
     * @param pid pid,不能为零
     * @return 最大id
     */
    private String generateNextId(String pid) {
        String maxId = sysMenuMapper.getMaxId(pid);
        return commonService.generateNextId(maxId, pid, CommonConstant.STEP_10, ErrorCode.MENU_MAX_ERROR);
    }
}
