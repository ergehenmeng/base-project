package com.eghm.app.manage.controller.sys;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.foundation.core.annotation.SkipPerm;
import com.eghm.foundation.core.dto.IdDTO;
import com.eghm.foundation.core.dto.ext.CheckBox;
import com.eghm.foundation.core.dto.ext.PageData;
import com.eghm.foundation.core.dto.ext.PagingQuery;
import com.eghm.foundation.core.dto.ext.RespBody;
import com.eghm.platform.iam.dto.RoleAddRequest;
import com.eghm.platform.iam.dto.RoleAuthRequest;
import com.eghm.platform.iam.dto.RoleEditRequest;
import com.eghm.foundation.core.enums.RoleType;
import com.eghm.platform.iam.service.SysRoleService;
import com.eghm.platform.iam.vo.SysRoleResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2018/11/26 15:21
 */
@RestController
@AllArgsConstructor
@Tag(name = "角色管理")
@RequestMapping(value = "/manage/role", produces = MediaType.APPLICATION_JSON_VALUE)
public class SysRoleController {

    private final SysRoleService sysRoleService;

    @GetMapping("/listPage")
    @Operation(summary = "角色列表(分页)")
    public RespBody<PageData<SysRoleResponse>> listPage(@ParameterObject PagingQuery request) {
        Page<SysRoleResponse> page = sysRoleService.getByPage(request);
        return RespBody.success(PageData.convert(page));
    }

    @GetMapping("/list")
    @Operation(summary = "角色列表(不分页)")
    @SkipPerm
    public RespBody<List<CheckBox>> list() {
        List<CheckBox> roleList = sysRoleService.getList();
        return RespBody.success(roleList);
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "编辑")
    public RespBody<Void> update(@Validated @RequestBody RoleEditRequest request) {
        sysRoleService.update(request);
        return RespBody.success();
    }

    @PostMapping(value = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "删除")
    public RespBody<Void> delete(@Validated @RequestBody IdDTO dto) {
        sysRoleService.delete(dto.getId());
        return RespBody.success();
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "新增")
    public RespBody<Void> create(@Validated @RequestBody RoleAddRequest request) {
        request.setRoleType(RoleType.COMMON);
        sysRoleService.create(request);
        return RespBody.success();
    }

    @GetMapping("/menu")
    @Operation(summary = "查询角色关联菜单列表")
    public RespBody<List<String>> menu(@ParameterObject @Validated IdDTO dto) {
        List<String> menuIds = sysRoleService.getRoleMenu(dto.getId());
        return RespBody.success(menuIds);
    }

    @PostMapping(value = "/auth", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "角色菜单授权")
    public RespBody<Void> authRole(@Validated @RequestBody RoleAuthRequest request) {
        sysRoleService.authMenu(request.getRoleId(), request.getMenuIds());
        return RespBody.success();
    }
}
