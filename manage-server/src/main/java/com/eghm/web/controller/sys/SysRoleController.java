package com.eghm.web.controller.sys;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.annotation.SkipPerm;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.dto.IdDTO;
import com.eghm.dto.ext.CheckBox;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.PagingQuery;
import com.eghm.dto.ext.RespBody;
import com.eghm.dto.sys.role.RoleAddRequest;
import com.eghm.dto.sys.role.RoleAuthRequest;
import com.eghm.dto.sys.role.RoleEditRequest;
import com.eghm.enums.RoleType;
import com.eghm.service.sys.SysRoleService;
import com.eghm.vo.sys.SysRoleResponse;
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
@Tag(name="角色管理")
@AllArgsConstructor
@RequestMapping(value = "/manage/role", produces = MediaType.APPLICATION_JSON_VALUE)
public class SysRoleController {

    private final SysRoleService sysRoleService;

    @GetMapping("/listPage")
    @Operation(summary = "角色列表(分页)")
    public RespBody<PageData<SysRoleResponse>> listPage(@ParameterObject PagingQuery request) {
        Page<SysRoleResponse> page = sysRoleService.getByPage(request);
        return RespBody.success(PageData.toPage(page));
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
        sysRoleService.delete(dto.getId(), SecurityHolder.getMerchantId());
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
