package com.eghm.service.sys.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.common.constant.CommonConstant;
import com.eghm.common.enums.ErrorCode;
import com.eghm.common.exception.BusinessException;
import com.eghm.dao.mapper.SysMenuMapper;
import com.eghm.dao.model.SysMenu;
import com.eghm.model.dto.menu.MenuAddRequest;
import com.eghm.model.dto.menu.MenuEditRequest;
import com.eghm.service.sys.SysMenuService;
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

    private final Comparator<SysMenu> comparator = Comparator.comparing(SysMenu::getSort);

    /**
     * 步长默认3位数即 1~999: 同级别最多有999个菜单
     */
    private static final String STEP = "001";

    @Override
    public List<SysMenu> getMenuList(Long operatorId) {
        List<SysMenu> list = sysMenuMapper.getMenuList(operatorId);
        return list.stream()
                .filter(parent -> CommonConstant.ROOT.equals(parent.getPid()))
                .peek(parent -> setChild(parent, list))
                .sorted(comparator).collect(Collectors.toList());
    }

    @Override
    public List<SysMenu> getButtonList(Long operatorId) {
        return sysMenuMapper.getButtonList(operatorId);
    }

    @Override
    public List<SysMenu> getList(Long operatorId) {
        return sysMenuMapper.getList(operatorId);
    }

    @Override
    public SysMenu getMenuById(Long id) {
        return sysMenuMapper.selectById(id);
    }

    @Override
    public List<SysMenu> getAllList() {
        return sysMenuMapper.getAllList();
    }

    @Override
    public void addMenu(MenuAddRequest request) {
        this.checkNidRedo(request.getNid());
        SysMenu copy = DataUtil.copy(request, SysMenu.class);
        copy.setId(this.generateNextId(request.getPid()));
        sysMenuMapper.insert(copy);
    }

    @Override
    public void updateMenu(MenuEditRequest request) {
        SysMenu copy = DataUtil.copy(request, SysMenu.class);
        sysMenuMapper.updateById(copy);
    }

    @Override
    public void deleteMenu(Long id) {
        sysMenuMapper.deleteById(id);
    }

    @Override
    public List<String> getAuthByOperatorId(Long operator) {
        List<SysMenu> menuList = sysMenuMapper.getList(operator);
        return menuList.stream().map(SysMenu::getNid).collect(Collectors.toList());
    }

    /**
     * 生成pid子菜单中下一个最大id
     * @param pid pid,不能为零
     * @return 最大id
     */
    private String generateNextId(String pid) {
        String maxId = sysMenuMapper.getMaxId(pid);
        // 空表示当前菜单没有子菜单,直接生成第一个子菜单
        if (StrUtil.isBlank(maxId)) {
            return pid + STEP;
        }
        // 最大子菜单+1即可
        return String.valueOf(Long.parseLong(maxId) + 1);
    }

    /**
     * 校验nid是否唯一
     * @param nid nid
     */
    private void checkNidRedo(String nid) {
        LambdaQueryWrapper<SysMenu> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SysMenu::getNid, nid);
        Integer count = sysMenuMapper.selectCount(wrapper);
        if (count > 0) {
            log.error("菜单标示符被占用 [{}]", nid);
            throw new BusinessException(ErrorCode.MENU_NID_ERROR);
        }
    }

    /**
     * 设置子菜单列表
     * 注意:此方法有一定性能问题, 菜单被匹配后并未从list移除,会导致更多迭代次数
     * @param parent 当前菜单
     * @param list   所有用户可操作的菜单
     */
    private void setChild(SysMenu parent, List<SysMenu> list) {
        List<SysMenu> childList = list.stream()
                .filter(item -> parent.getId().equals(item.getPid()))
                .peek(item -> setChild(item, list))
                .sorted(comparator).collect(Collectors.toList());
        parent.setChildren(childList);
    }

}
