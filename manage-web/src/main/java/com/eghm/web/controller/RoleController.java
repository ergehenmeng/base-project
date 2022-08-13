package com.eghm.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dao.model.SysRole;
import com.eghm.model.dto.IdDTO;
import com.eghm.model.dto.ext.CheckBox;
import com.eghm.model.dto.ext.PageData;
import com.eghm.model.dto.ext.RespBody;
import com.eghm.model.dto.role.RoleAddRequest;
import com.eghm.model.dto.role.RoleAuthRequest;
import com.eghm.model.dto.role.RoleEditRequest;
import com.eghm.model.dto.role.RoleQueryRequest;
import com.eghm.service.sys.SysRoleService;
import com.eghm.utils.DataUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2018/11/26 15:21
 */
@RestController
@Api(tags = "角色管理")
@AllArgsConstructor
@RequestMapping("/manage/role")
public class RoleController {

    private final SysRoleService sysRoleService;

    @GetMapping("/listPage")
    @ApiOperation("角色列表(分页)")
    public PageData<SysRole> listPage(RoleQueryRequest request) {
        Page<SysRole> page = sysRoleService.getByPage(request);
        return PageData.toPage(page);
    }

    @PostMapping("/list")
    @ApiOperation("角色列表(不分页)")
    public List<CheckBox> list() {
        List<SysRole> list = sysRoleService.getList();
        //将角色列表转换为checkBox所能识别的列表同时封装为ReturnJson对象
        return DataUtil.convert(list, sysRole -> CheckBox.builder().hide(sysRole.getId()).show(sysRole.getRoleName()).build());
    }

    @PostMapping("/update")
    @ApiOperation("编辑角色")
    public RespBody<Void> update(@Validated @RequestBody RoleEditRequest request) {
        sysRoleService.update(request);
        return RespBody.success();
    }

    @PostMapping("/delete")
    @ApiOperation("编辑角色")
    @ApiImplicitParam(name = "id", value = "id主键", required = true)
    public RespBody<Void> delete(@Validated @RequestBody IdDTO dto) {
        sysRoleService.delete(dto.getId());
        return RespBody.success();
    }

    @PostMapping("/create")
    @ApiOperation("添加角色")
    public RespBody<Void> create(@Validated @RequestBody RoleAddRequest request) {
        sysRoleService.create(request);
        return RespBody.success();
    }

    @PostMapping("/auth")
    @ApiOperation("角色菜单授权")
    public RespBody<Void> authRole(@Validated @RequestBody RoleAuthRequest request) {
        sysRoleService.authMenu(request.getRoleId(), request.getMenuIds());
        return RespBody.success();
    }
}
