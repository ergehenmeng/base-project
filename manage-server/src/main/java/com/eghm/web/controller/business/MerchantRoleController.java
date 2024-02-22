package com.eghm.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.dto.IdDTO;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.dto.role.RoleAddRequest;
import com.eghm.dto.role.RoleAuthRequest;
import com.eghm.dto.role.RoleEditRequest;
import com.eghm.dto.role.RoleQueryRequest;
import com.eghm.enums.ref.RoleType;
import com.eghm.model.SysRole;
import com.eghm.service.sys.SysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2024/1/26
 */
@RestController
@Api(tags = "商户角色管理")
@AllArgsConstructor
@RequestMapping("/manage/merchant/role")
public class MerchantRoleController {

    private final SysRoleService sysRoleService;

    @GetMapping("/listPage")
    @ApiOperation("角色列表(分页)")
    public RespBody<PageData<SysRole>> listPage(RoleQueryRequest request) {
        Page<SysRole> page = sysRoleService.getByPage(request);
        return RespBody.success(PageData.toPage(page));
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("添加角色")
    public RespBody<Void> create(@Validated @RequestBody RoleAddRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        request.setRoleType(RoleType.COMMON);
        sysRoleService.create(request);
        return RespBody.success();
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("编辑角色")
    public RespBody<Void> update(@Validated @RequestBody RoleEditRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        sysRoleService.update(request);
        return RespBody.success();
    }

    @PostMapping(value = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("编辑角色")
    public RespBody<Void> delete(@Validated @RequestBody IdDTO dto) {
        sysRoleService.delete(dto.getId(), SecurityHolder.getMerchantId());
        return RespBody.success();
    }

    @GetMapping("/menu")
    @ApiOperation("查询角色关联菜单列表")
    public RespBody<List<String>> menu(@Validated IdDTO dto) {
        List<String> menuIds = sysRoleService.getRoleMenu(dto.getId());
        return RespBody.success(menuIds);
    }

    @PostMapping(value = "/auth", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("角色菜单授权")
    public RespBody<Void> authRole(@Validated @RequestBody RoleAuthRequest request) {
        sysRoleService.authMenu(request.getRoleId(), request.getMenuIds());
        return RespBody.success();
    }
}
