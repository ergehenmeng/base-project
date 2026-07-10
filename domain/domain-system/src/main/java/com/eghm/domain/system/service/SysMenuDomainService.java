package com.eghm.domain.system.service;

import com.eghm.domain.shared.enums.ErrorCode;
import com.eghm.domain.shared.exception.BusinessException;
import com.eghm.domain.system.model.SysMenu;
import com.eghm.domain.system.repository.SysMenuRepository;

/**
 * 菜单领域服务.
 *
 * @author 二哥很猛
 */
public class SysMenuDomainService {

    public void assertTitleAvailable(SysMenuRepository repository, String pid, String title, String excludeId) {
        if (repository.existsTitle(pid, title, excludeId)) {
            throw new BusinessException(ErrorCode.MENU_TITLE_REDO);
        }
    }

    public void assertChildDisplayAllowed(SysMenuRepository repository, String pid, Integer displayState) {
        if (SysMenu.isRoot(pid)) {
            return;
        }
        SysMenu parent = repository.findById(pid);
        if (parent == null) {
            throw new BusinessException(ErrorCode.PID_MENU_NULL);
        }
        parent.assertCanCreateChild(displayState);
    }
}
