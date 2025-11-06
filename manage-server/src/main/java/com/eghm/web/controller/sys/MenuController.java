package com.eghm.web.controller.sys;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.IdDTO;
import com.eghm.dto.SortByDTO;
import com.eghm.dto.StateRequest;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.dto.sys.menu.MenuAddRequest;
import com.eghm.dto.sys.menu.MenuEditRequest;
import com.eghm.dto.sys.menu.MenuQueryRequest;
import com.eghm.enums.DisplayState;
import com.eghm.enums.RoleType;
import com.eghm.model.SysRole;
import com.eghm.service.sys.SysMenuService;
import com.eghm.service.sys.SysRoleService;
import com.eghm.vo.sys.menu.MenuFullResponse;
import com.eghm.vo.sys.menu.MenuResponse;
import com.eghm.vo.sys.menu.MenuTreeResponse;
import com.eghm.web.configuration.interceptor.PermInterceptor;
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
 * @since 2018/1/30 09:30
 */
@RestController
@Tag(name = "菜单管理")
@AllArgsConstructor
@RequestMapping(value = "/manage/menu", produces = MediaType.APPLICATION_JSON_VALUE)
public class MenuController {

    private final SysRoleService sysRoleService;

    private final SysMenuService sysMenuService;

    private final PermInterceptor permInterceptor;

    @GetMapping("/tree")
    @Operation(summary = "左侧菜单①")
    public RespBody<MenuTreeResponse> tree() {
        MenuTreeResponse response = sysMenuService.tree();
        return RespBody.success(response);
    }

    @GetMapping("/listPage")
    @Operation(summary = "分页菜单②")
    public RespBody<PageData<MenuResponse>> listPage(@ParameterObject @Validated MenuQueryRequest request) {
        Page<MenuResponse> byPage = sysMenuService.getByPage(request);
        return RespBody.success(PageData.convert(byPage));
    }

    @GetMapping("/list")
    @Operation(summary = "全部菜单❶")
    public RespBody<List<MenuFullResponse>> list(@ParameterObject MenuQueryRequest request) {
        List<MenuFullResponse> responseList = sysMenuService.getList(request);
        return RespBody.success(responseList);
    }

    @GetMapping("/systemList")
    @Operation(summary = "系统菜单(角色授权使用)")
    public RespBody<List<MenuTreeResponse>> systemList(IdDTO dto) {
        SysRole sysRole = sysRoleService.getById(dto.getId());
        int displayState = sysRole.getRoleType() == RoleType.COMMON ? DisplayState.SYSTEM.getValue() : DisplayState.MERCHANT.getValue();
        List<MenuTreeResponse> responseList = sysMenuService.getAll(displayState);
        return RespBody.success(responseList);
    }
    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "添加菜单")
    public synchronized RespBody<Void> create(@Validated @RequestBody MenuAddRequest request) {
        sysMenuService.create(request);
        permInterceptor.refresh();
        return RespBody.success();
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "修改菜单")
    public RespBody<Void> update(@Validated @RequestBody MenuEditRequest request) {
        sysMenuService.update(request);
        permInterceptor.refresh();
        return RespBody.success();
    }

    @PostMapping(value = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "删除菜单")
    public RespBody<Void> delete(@Validated @RequestBody IdDTO dto) {
        sysMenuService.delete(String.valueOf(dto.getId()));
        permInterceptor.refresh();
        return RespBody.success();
    }

    @PostMapping(value = "/sort", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "更新排序")
    public RespBody<Void> sort(@RequestBody @Validated SortByDTO dto) {
        sysMenuService.sortBy(String.valueOf(dto.getId()), dto.getSortBy());
        return RespBody.success();
    }

    @PostMapping(value = "/updateState", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "更新状态")
    public RespBody<Void> updateState(@Validated @RequestBody StateRequest request) {
        sysMenuService.updateState(String.valueOf(request.getId()), request.getState());
        permInterceptor.refresh();
        return RespBody.success();
    }

}
