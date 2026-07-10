package com.eghm.application.system.service.impl;

import com.eghm.application.shared.common.CommonService;
import com.eghm.constants.CommonConstant;
import com.eghm.application.shared.dto.sys.menu.MenuAddRequest;
import com.eghm.application.shared.dto.sys.menu.MenuEditRequest;
import com.eghm.domain.shared.enums.ErrorCode;
import com.eghm.domain.shared.exception.BusinessException;
import com.eghm.application.system.service.SysMenuApplicationService;
import com.eghm.domain.system.model.SysMenu;
import com.eghm.domain.system.repository.SysMenuRepository;
import com.eghm.domain.system.service.SysMenuDomainService;
import com.eghm.application.shared.utils.DataUtil;
import com.eghm.application.shared.utils.StringUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @since 2018/1/26 16:15
 */
@Slf4j
@AllArgsConstructor
@Service("sysMenuService")
public class SysMenuApplicationServiceImpl implements SysMenuApplicationService {

    private final SysMenuRepository sysMenuRepository;

    private final CommonService commonService;

    private static final SysMenuDomainService SYS_MENU_DOMAIN_SERVICE = new SysMenuDomainService();

    @Override
    public void create(MenuAddRequest request) {
        SYS_MENU_DOMAIN_SERVICE.assertTitleAvailable(sysMenuRepository, request.getPid(), request.getTitle(), null);
        SYS_MENU_DOMAIN_SERVICE.assertChildDisplayAllowed(sysMenuRepository, request.getPid(), request.getDisplayState());
        SysMenu menu = DataUtil.copy(request, SysMenu.class);
        String id = String.valueOf(this.generateNextId(request.getPid()));
        menu.assignIdentity(id, StringUtil.encryptNumber(Long.parseLong(id)));
        sysMenuRepository.save(menu);
    }

    @Override
    public void update(MenuEditRequest request) {
        SYS_MENU_DOMAIN_SERVICE.assertTitleAvailable(sysMenuRepository, request.getPid(), request.getTitle(), request.getId());
        SYS_MENU_DOMAIN_SERVICE.assertChildDisplayAllowed(sysMenuRepository, request.getPid(), request.getDisplayState());
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

    private String generateNextId(String pid) {
        String maxId = sysMenuRepository.findMaxId(pid);
        return commonService.generateNextId(maxId, pid, CommonConstant.STEP_10, ErrorCode.MENU_MAX_ERROR);
    }
}
