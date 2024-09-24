package com.eghm.service.sys.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.dto.menu.MenuAddRequest;
import com.eghm.dto.menu.MenuEditRequest;
import com.eghm.dto.menu.MenuQueryRequest;
import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.SysMenuMapper;
import com.eghm.model.SysMenu;
import com.eghm.service.sys.SysMenuService;
import com.eghm.utils.DataUtil;
import com.eghm.utils.StringUtil;
import com.eghm.vo.menu.MenuFullResponse;
import com.eghm.vo.menu.MenuResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author 二哥很猛
 * @since 2018/1/26 16:15
 */
@Service("sysMenuService")
@AllArgsConstructor
@Slf4j
public class SysMenuServiceImpl implements SysMenuService {

    /**
     * 步长默认2位数即 10~99
     */
    private static final String STEP = "10";
    /**
     * 同级别最多有90个菜单
     */
    private static final int MAX = 90;

    /**
     * 根节点
     */
    private static final String ROOT = "0";

    private final SysMenuMapper sysMenuMapper;

    private final Comparator<MenuResponse> comparator = Comparator.comparing(MenuResponse::getSort);

    private final Comparator<MenuFullResponse> fullComparator = Comparator.comparing(MenuFullResponse::getSort);

    @Override
    public List<MenuResponse> getLeftMenuList(Long userId) {
        List<MenuResponse> list = sysMenuMapper.getMenuList(userId, 1);
        return this.treeBin(ROOT, list);
    }

    @Override
    public List<MenuResponse> getAdminLeftMenuList() {
        List<MenuResponse> list = sysMenuMapper.getAdminMenuList(1);
        return this.treeBin(ROOT, list);
    }

    @Override
    public List<MenuResponse> getSystemList() {
        List<MenuResponse> responseList = sysMenuMapper.getAdminMenuList(null);
        return this.treeBin(ROOT, responseList);
    }

    @Override
    public List<MenuResponse> getMerchantList(Long userId) {
        List<MenuResponse> responseList = sysMenuMapper.getMerchantMenuList(userId);
        return this.treeBin(ROOT, responseList);
    }

    @Override
    public List<MenuFullResponse> getList(MenuQueryRequest request) {
        List<MenuFullResponse> responseList = sysMenuMapper.getList(request);
        return this.treeBinB(ROOT, responseList);
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
        this.redoTitle(request.getTitle(), request.getPid(), null);
        this.checkDisplayState(request.getPid(), request.getDisplayState());
        SysMenu copy = DataUtil.copy(request, SysMenu.class);
        copy.setId(String.valueOf(this.generateNextId(request.getPid())));
        copy.setCode(StringUtil.encryptNumber(Long.parseLong(copy.getId())));
        sysMenuMapper.insert(copy);
    }

    @Override
    public void update(MenuEditRequest request) {
        this.redoTitle(request.getTitle(), request.getPid(), request.getId());
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
        List<MenuResponse> menuList = sysMenuMapper.getMenuList(userId, 2);
        return menuList.stream().map(MenuResponse::getCode).collect(Collectors.toList());
    }

    @Override
    public List<String> getAdminPermCode() {
        List<MenuResponse> menuList = sysMenuMapper.getAdminMenuList(2);
        return menuList.stream().map(MenuResponse::getCode).collect(Collectors.toList());
    }

    /**
     * 校验菜单名称是否重复
     *
     * @param title 菜单名称
     * @param id    菜单id
     */
    private void redoTitle(String title, String pid, String id) {
        LambdaQueryWrapper<SysMenu> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SysMenu::getTitle, title);
        wrapper.eq(SysMenu::getPid, pid);
        wrapper.ne(id != null, SysMenu::getId, id);
        Long count = sysMenuMapper.selectCount(wrapper);
        if (count > 0) {
            throw new BusinessException(ErrorCode.MENU_TITLE_REDO);
        }
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
        if (sysMenu.getDisplayState() != 3 && !sysMenu.getDisplayState().equals(displayState)) {
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
     * 将菜单列表树化
     *
     * @param menuList 菜单列表
     * @return 菜单列表 树状结构
     */
    private List<MenuResponse> treeBin(String pid, List<MenuResponse> menuList) {
        return menuList.stream()
                .filter(parent -> Objects.equals(pid, parent.getPid()))
                .peek(parent -> parent.setChildren(this.treeBin(parent.getId(), menuList)))
                .sorted(comparator).collect(Collectors.toList());
    }

    /**
     * 将菜单列表树化
     *
     * @param menuList 菜单列表
     * @return 菜单列表 树状结构
     */
    private List<MenuFullResponse> treeBinB(String pid, List<MenuFullResponse> menuList) {
        return menuList.stream()
                .filter(parent -> Objects.equals(pid, parent.getPid()))
                .peek(parent -> parent.setChildren(this.treeBinB(parent.getId(), menuList)))
                .sorted(fullComparator).collect(Collectors.toList());
    }
}
