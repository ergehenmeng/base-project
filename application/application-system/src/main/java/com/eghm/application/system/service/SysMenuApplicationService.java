package com.eghm.application.system.service;

import com.eghm.application.shared.common.CommonService;
import com.eghm.application.shared.dto.sys.menu.MenuAddRequest;
import com.eghm.application.shared.dto.sys.menu.MenuEditRequest;
import com.eghm.application.shared.utils.DataUtil;
import com.eghm.application.shared.utils.StringUtil;
import com.eghm.constants.CommonConstant;
import com.eghm.domain.shared.enums.ErrorCode;
import com.eghm.domain.system.model.SysMenu;
import com.eghm.domain.system.repository.SysMenuRepository;
import com.eghm.domain.system.service.SysMenuDomainService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @since 2018/1/26 16:15
 */
@Slf4j
@Service
@AllArgsConstructor
public class SysMenuApplicationService {

    private final SysMenuRepository sysMenuRepository;

    private final CommonService commonService;

    private static final SysMenuDomainService SYS_MENU_DOMAIN_SERVICE = new SysMenuDomainService();

    /**
     * 添加菜单
     *
     * @param request 要添加的菜单信息
     */
    public void create(MenuAddRequest request) {
        SYS_MENU_DOMAIN_SERVICE.assertTitleAvailable(sysMenuRepository, request.getPid(), request.getTitle(), null);
        SYS_MENU_DOMAIN_SERVICE.assertChildDisplayAllowed(sysMenuRepository, request.getPid(), request.getDisplayState());
        SysMenu menu = DataUtil.copy(request, SysMenu.class);
        String id = String.valueOf(this.generateNextId(request.getPid()));
        menu.assignIdentity(id, StringUtil.encryptNumber(Long.parseLong(id)));
        sysMenuRepository.save(menu);
    }

    /**
     * 更新菜单信息
     *
     * @param request 要更新的菜单信息
     */
    public void update(MenuEditRequest request) {
        SYS_MENU_DOMAIN_SERVICE.assertTitleAvailable(sysMenuRepository, request.getPid(), request.getTitle(), request.getId());
        SYS_MENU_DOMAIN_SERVICE.assertChildDisplayAllowed(sysMenuRepository, request.getPid(), request.getDisplayState());
        SysMenu menu = DataUtil.copy(request, SysMenu.class);
        menu.changeCode(StringUtil.encryptNumber(Long.parseLong(menu.getId())));
        sysMenuRepository.update(menu);
    }

    /**
     * 根据主键删除菜单
     *
     * @param id 主键
     */
    public void delete(String id) {
        sysMenuRepository.deleteById(id);
    }

    /**
     * 更新菜单状态
     *
     * @param id    id
     * @param state false:禁用 true:启用
     */
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

    /**
     * 更新菜单排序
     *
     * @param id     id
     * @param sortBy 排序
     */
    public void sortBy(String id, Integer sortBy) {
        sysMenuRepository.updateSort(id, sortBy);
    }

    private String generateNextId(String pid) {
        String maxId = sysMenuRepository.findMaxId(pid);
        return commonService.generateNextId(maxId, pid, CommonConstant.STEP_10, ErrorCode.MENU_MAX_ERROR);
    }
}
