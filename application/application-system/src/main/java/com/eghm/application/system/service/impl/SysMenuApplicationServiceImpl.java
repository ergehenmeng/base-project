package com.eghm.application.system.service.impl;

import com.eghm.application.shared.common.CommonService;
import com.eghm.constants.CommonConstant;
import com.eghm.application.shared.dto.sys.menu.MenuAddRequest;
import com.eghm.application.shared.dto.sys.menu.MenuEditRequest;
import com.eghm.application.shared.dto.sys.menu.MenuQueryRequest;
import com.eghm.domain.shared.enums.ErrorCode;
import com.eghm.domain.shared.exception.BusinessException;
import com.eghm.application.system.query.SysMenuQueryService;
import com.eghm.application.system.service.SysMenuApplicationService;
import com.eghm.domain.system.model.SysMenu;
import com.eghm.domain.system.repository.SysMenuRepository;
import com.eghm.application.shared.utils.DataUtil;
import com.eghm.application.shared.utils.StringUtil;
import com.eghm.application.shared.utils.TreeUtil;
import com.eghm.application.shared.vo.sys.menu.MenuFullResponse;
import com.eghm.application.shared.vo.sys.menu.MenuTreeResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2018/1/26 16:15
 */
@Slf4j
@AllArgsConstructor
@Service("sysMenuService")
public class SysMenuApplicationServiceImpl implements SysMenuApplicationService {

    /**
     * 根节点
     */
    private static final String ROOT = SysMenu.ROOT;

    private static final Comparator<MenuTreeResponse> COMPARATOR = Comparator.comparing(MenuTreeResponse::getSort);

    private final SysMenuRepository sysMenuRepository;

    private final SysMenuQueryService sysMenuQueryService;

    private final CommonService commonService;

    @Override
    public MenuTreeResponse tree() {
        List<MenuTreeResponse> responseList = sysMenuQueryService.getLeftList();
        List<MenuTreeResponse> treeBin = TreeUtil.tree(responseList, ROOT, MenuTreeResponse::getId, MenuTreeResponse::getPid, MenuTreeResponse::setChildren, COMPARATOR);
        MenuTreeResponse response = new MenuTreeResponse();
        response.setChildren(treeBin);
        response.setId(ROOT);
        response.setTitle("系统菜单");
        return response;
    }

    @Override
    public List<MenuTreeResponse> getLeftMenuList(Long userId) {
        List<MenuTreeResponse> list = sysMenuQueryService.getMenuList(userId, 1);
        return TreeUtil.tree(list, ROOT, MenuTreeResponse::getId, MenuTreeResponse::getPid, MenuTreeResponse::setChildren, COMPARATOR);
    }

    @Override
    public List<MenuTreeResponse> getAdminLeftMenuList() {
        List<MenuTreeResponse> list = sysMenuQueryService.getSystemMenuList(1);
        return TreeUtil.tree(list, ROOT, MenuTreeResponse::getId, MenuTreeResponse::getPid, MenuTreeResponse::setChildren, COMPARATOR);
    }

    @Override
    public List<MenuTreeResponse> getAll(Integer displayState) {
        List<MenuTreeResponse> responseList = sysMenuQueryService.getAll(displayState);
        return TreeUtil.tree(responseList, ROOT, MenuTreeResponse::getId, MenuTreeResponse::getPid, MenuTreeResponse::setChildren, COMPARATOR);
    }

    @Override
    public List<MenuFullResponse> getList(MenuQueryRequest request) {
        if (StringUtil.isNotBlank(request.getQueryName())) {
            request.setPid(null);
        }
        return sysMenuQueryService.getList(request);
    }

    @Override
    public List<SysMenu> getButtonList() {
        return sysMenuRepository.findEnabledButtons();
    }

    @Override
    public void create(MenuAddRequest request) {
        if (sysMenuRepository.existsTitle(request.getPid(), request.getTitle(), null)) {
            log.warn("菜单名称重复 [{}] [{}]", request.getPid(), request.getTitle());
            throw new BusinessException(ErrorCode.MENU_TITLE_REDO);
        }
        this.checkDisplayState(request.getPid(), request.getDisplayState());
        SysMenu menu = DataUtil.copy(request, SysMenu.class);
        String id = String.valueOf(this.generateNextId(request.getPid()));
        menu.assignIdentity(id, StringUtil.encryptNumber(Long.parseLong(id)));
        sysMenuRepository.save(menu);
    }

    @Override
    public void update(MenuEditRequest request) {
        if (sysMenuRepository.existsTitle(request.getPid(), request.getTitle(), request.getId())) {
            log.warn("菜单名称重复 [{}] [{}]", request.getPid(), request.getTitle());
            throw new BusinessException(ErrorCode.MENU_TITLE_REDO);
        }
        this.checkDisplayState(request.getPid(), request.getDisplayState());
        SysMenu menu = DataUtil.copy(request, SysMenu.class);
        menu.changeCode(StringUtil.encryptNumber(Long.parseLong(menu.getId())));
        sysMenuRepository.update(menu);
    }

    @Override
    public void delete(String id) {
        sysMenuRepository.deleteById(id);
    }

    @Override
    public void updateState(String id, Boolean state) {
        SysMenu menu = sysMenuRepository.findById(id);
        if (menu == null) {
            return;
        }
        if (Boolean.TRUE.equals(state)) {
            menu.enable();
        } else {
            menu.disable();
        }
        sysMenuRepository.updateState(menu.getId(), menu.getState());
    }

    @Override
    public void sortBy(String id, Integer sortBy) {
        sysMenuRepository.updateSort(id, sortBy);
    }

    @Override
    public List<String> getPermCode(Long userId) {
        List<MenuTreeResponse> menuList = sysMenuQueryService.getMenuList(userId, 2);
        return menuList.stream().map(MenuTreeResponse::getCode).toList();
    }

    @Override
    public List<String> getAdminPermCode() {
        List<MenuTreeResponse> menuList = sysMenuQueryService.getSystemMenuList(2);
        return menuList.stream().map(MenuTreeResponse::getCode).toList();
    }

    private void checkDisplayState(String pid, Integer displayState) {
        if (SysMenu.isRoot(pid)) {
            return;
        }
        SysMenu parent = sysMenuRepository.findById(pid);
        if (parent == null) {
            log.warn("父菜单节点不存在 [{}]", pid);
            throw new BusinessException(ErrorCode.PID_MENU_NULL);
        }
        try {
            parent.assertCanCreateChild(displayState);
        } catch (BusinessException e) {
            log.warn("菜单节点显示状态不满足要求 [{}] [{}] [{}]", pid, parent.getDisplayState(), displayState);
            throw e;
        }
    }

    private String generateNextId(String pid) {
        String maxId = sysMenuRepository.findMaxId(pid);
        return commonService.generateNextId(maxId, pid, CommonConstant.STEP_10, ErrorCode.MENU_MAX_ERROR);
    }
}
