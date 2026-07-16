package com.eghm.app.manage.controller.sys;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.foundation.core.constants.LockConstant;
import com.eghm.foundation.core.dto.IdDTO;
import com.eghm.foundation.core.dto.SortByDTO;
import com.eghm.foundation.core.dto.StateRequest;
import com.eghm.foundation.core.dto.ext.PageData;
import com.eghm.foundation.core.dto.ext.RespBody;
import com.eghm.platform.iam.dto.MenuAddRequest;
import com.eghm.platform.iam.dto.MenuEditRequest;
import com.eghm.platform.iam.dto.MenuQueryRequest;
import com.eghm.foundation.core.enums.DisplayState;
import com.eghm.foundation.core.enums.RoleType;
import com.eghm.platform.iam.event.PermissionRefreshEvent;
import com.eghm.foundation.core.lock.RedisLock;
import com.eghm.platform.iam.entity.SysRole;
import com.eghm.platform.iam.service.SysMenuService;
import com.eghm.platform.iam.service.SysRoleService;
import com.eghm.platform.iam.vo.MenuFullResponse;
import com.eghm.platform.iam.vo.MenuResponse;
import com.eghm.platform.iam.vo.MenuTreeResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2018/1/30 09:30
 */
@RestController
@AllArgsConstructor
@Tag(name = "菜单管理")
@RequestMapping(value = "/manage/menu", produces = MediaType.APPLICATION_JSON_VALUE)
public class MenuController {
    
    private final RedisLock redisLock;

    private final SysRoleService sysRoleService;

    private final SysMenuService sysMenuService;

    private final ApplicationEventPublisher eventPublisher;

    @GetMapping("/tree")
    @Operation(summary = "左侧菜单-v2-①")
    public RespBody<MenuTreeResponse> tree() {
        MenuTreeResponse response = sysMenuService.tree();
        return RespBody.success(response);
    }

    @GetMapping("/listPage")
    @Operation(summary = "分页菜单-v2-②")
    public RespBody<PageData<MenuResponse>> listPage(@ParameterObject @Validated MenuQueryRequest request) {
        Page<MenuResponse> byPage = sysMenuService.getByPage(request);
        return RespBody.success(PageData.convert(byPage));
    }

    @GetMapping("/list")
    @Operation(summary = "全部菜单-v1")
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
    public RespBody<Void> create(@Validated @RequestBody MenuAddRequest request) {
        redisLock.lock(LockConstant.MENU_LOCK, 10000, () -> {
            sysMenuService.create(request);
            eventPublisher.publishEvent(new PermissionRefreshEvent());
        });
        return RespBody.success();
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "修改菜单")
    public RespBody<Void> update(@Validated @RequestBody MenuEditRequest request) {
        sysMenuService.update(request);
        eventPublisher.publishEvent(new PermissionRefreshEvent());
        return RespBody.success();
    }

    @PostMapping(value = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "删除菜单")
    public RespBody<Void> delete(@Validated @RequestBody IdDTO dto) {
        sysMenuService.delete(String.valueOf(dto.getId()));
        eventPublisher.publishEvent(new PermissionRefreshEvent());
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
        eventPublisher.publishEvent(new PermissionRefreshEvent());
        return RespBody.success();
    }

}