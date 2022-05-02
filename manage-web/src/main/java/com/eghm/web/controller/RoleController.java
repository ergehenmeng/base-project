package com.eghm.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dao.model.SysRole;
import com.eghm.model.dto.ext.CheckBox;
import com.eghm.model.dto.ext.Paging;
import com.eghm.model.dto.ext.RespBody;
import com.eghm.model.dto.role.RoleAddRequest;
import com.eghm.model.dto.role.RoleEditRequest;
import com.eghm.model.dto.role.RoleQueryRequest;
import com.eghm.service.sys.SysRoleService;
import com.eghm.utils.DataUtil;
import com.eghm.web.annotation.Mark;
import com.google.common.base.Joiner;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @author 二哥很猛
 * @date 2018/11/26 15:21
 */
@RestController
@Api(tags = "角色管理")
@AllArgsConstructor
public class RoleController {

    private final SysRoleService sysRoleService;

    /**
     * 根据条件分页查询角色列表
     *
     * @param request 查询条件
     * @return 列表
     */
    @GetMapping("/role/list_page")
    @ApiOperation("角色列表(分页)")
    public Paging<SysRole> listPage(RoleQueryRequest request) {
        Page<SysRole> page = sysRoleService.getByPage(request);
        return new Paging<>(page);
    }

    /**
     * 获取所有可用的角色列表
     *
     * @return 角色列表
     */
    @PostMapping("/role/list")
    @ApiOperation("角色列表(不分页)")
    public List<CheckBox> list() {
        List<SysRole> list = sysRoleService.getList();
        //将角色列表转换为checkBox所能识别的列表同时封装为ReturnJson对象
        return DataUtil.convert(list, sysRole -> CheckBox.builder().hide(sysRole.getId()).show(sysRole.getRoleName()).build());
    }

    /**
     * 更新角色信息
     *
     * @param request 前台请求参数
     * @return 成功
     */
    @PostMapping("/role/edit")
    @Mark
    @ApiOperation("编辑角色")
    public RespBody<Object> edit(@Valid RoleEditRequest request) {
        sysRoleService.updateRole(request);
        return RespBody.success();
    }

    /**
     * 删除角色信息
     *
     * @param id 主键
     * @return 成功
     */
    @PostMapping("/role/delete")
    @Mark
    @ApiOperation("编辑角色")
    @ApiImplicitParam(name = "id", value = "id主键", required = true)
    public RespBody<Object> delete(Long id) {
        sysRoleService.deleteRole(id);
        return RespBody.success();
    }

    /**
     * 添加角色信息
     *
     * @param request 前台参数
     * @return 成功
     */
    @PostMapping("/role/add")
    @Mark
    @ApiOperation("添加角色")
    public RespBody<Object> add(@Valid RoleAddRequest request) {
        sysRoleService.addRole(request);
        return RespBody.success();
    }

    /**
     * 角色 查询角色要拥有的菜单列表
     * @param id    角色id
     * @return 角色编辑信息
     */
    @GetMapping("/role/menu_list")
    public String menuList(Long id) {
        List<Long> role = sysRoleService.getRoleMenu(id);
        String menuIds = Joiner.on(",").join(role);
        return "role/auth_page";
    }

    /**
     * 保存角色菜单关联信息
     *
     * @param roleId  角色id
     * @param menuIds 菜单
     * @return 响应
     */
    @PostMapping("/role/auth")
    @Mark
    @ApiOperation("角色菜单授权")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", required = true, value = "角色id"),
            @ApiImplicitParam(name = "menuIds", required = true, value = "菜单id,逗号分割")})
    public RespBody<Object> authRole(@RequestParam("roleId") Long roleId, @RequestParam("menuIds") String menuIds) {
        sysRoleService.authMenu(roleId, menuIds);
        return RespBody.success();
    }
}
