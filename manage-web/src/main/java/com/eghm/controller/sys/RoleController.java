package com.eghm.controller.sys;

import com.eghm.annotation.Mark;
import com.eghm.dao.model.SysRole;
import com.eghm.model.dto.role.RoleAddRequest;
import com.eghm.model.dto.role.RoleEditRequest;
import com.eghm.model.dto.role.RoleQueryRequest;
import com.eghm.model.ext.CheckBox;
import com.eghm.model.ext.Paging;
import com.eghm.model.ext.RespBody;
import com.eghm.service.sys.SysRoleService;
import com.eghm.utils.DataUtil;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Joiner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2018/11/26 15:21
 */
@Controller
public class RoleController {

    private SysRoleService sysRoleService;

    @Autowired
    public void setSysRoleService(SysRoleService sysRoleService) {
        this.sysRoleService = sysRoleService;
    }

    /**
     * 根据条件分页查询角色列表
     *
     * @param request 查询条件
     * @return 列表
     */
    @PostMapping("/sys/role/list_page")
    @ResponseBody
    @Mark
    public Paging<SysRole> listPage(RoleQueryRequest request) {
        PageInfo<SysRole> page = sysRoleService.getByPage(request);
        return new Paging<>(page);
    }

    /**
     * 获取所有可用的角色列表
     *
     * @return 角色列表
     */
    @PostMapping("/sys/role/list")
    @ResponseBody
    @Mark
    public List<CheckBox> list() {
        List<SysRole> list = sysRoleService.getList();
        //将角色列表转换为checkBox所能识别的列表同时封装为ReturnJson对象
        return DataUtil.convert(list, sysRole -> CheckBox.builder().hide(sysRole.getId()).show(sysRole.getRoleName()).build());
    }

    /**
     * 编辑角色信息
     *
     * @param id 角色id
     * @return 角色编辑信息
     */
    @GetMapping("/sys/role/edit_page")
    @Mark
    public String editPage(Model model, Integer id) {
        SysRole role = sysRoleService.getById(id);
        model.addAttribute("role", role);
        return "sys/role/edit_page";
    }

    /**
     * 更新角色信息
     *
     * @param request 前台请求参数
     * @return 成功
     */
    @PostMapping("/sys/role/edit")
    @ResponseBody
    @Mark
    public RespBody<Object> edit(RoleEditRequest request) {
        sysRoleService.updateRole(request);
        return RespBody.success();
    }

    /**
     * 删除角色信息
     *
     * @param id 主键
     * @return 成功
     */
    @PostMapping("/sys/role/delete")
    @ResponseBody
    @Mark
    public RespBody<Object> delete(Integer id) {
        sysRoleService.deleteRole(id);
        return RespBody.success();
    }

    /**
     * 添加角色信息
     *
     * @param request 前台参数
     * @return 成功
     */
    @PostMapping("/sys/role/add")
    @ResponseBody
    @Mark
    public RespBody<Object> add(RoleAddRequest request) {
        sysRoleService.addRole(request);
        return RespBody.success();
    }

    /**
     * 角色授权页面
     *
     * @param model model
     * @param id    角色id
     * @return 角色编辑信息
     */
    @GetMapping("/sys/role/auth_page")
    @Mark
    public String addPage(Model model, Integer id) {
        List<Integer> role = sysRoleService.getRoleMenu(id);
        String menuIds = Joiner.on(",").join(role);
        model.addAttribute("menuIds", menuIds);
        model.addAttribute("roleId", id);
        return "sys/role/auth_page";
    }

    /**
     * 保存角色菜单关联信息
     *
     * @param roleId  角色id
     * @param menuIds 菜单
     * @return 响应
     */
    @PostMapping("/sys/role/auth")
    @ResponseBody
    @Mark
    public RespBody<Object> authRole(Integer roleId, String menuIds) {
        sysRoleService.authMenu(roleId, menuIds);
        return RespBody.success();
    }
}
