package com.eghm.web.controller;

import com.eghm.configuration.security.SecurityHolder;
import com.eghm.dto.IdDTO;
import com.eghm.dto.IdStateDTO;
import com.eghm.dto.ext.RespBody;
import com.eghm.dto.menu.MenuAddRequest;
import com.eghm.dto.menu.MenuEditRequest;
import com.eghm.vo.menu.MenuResponse;
import com.eghm.service.sys.SysMenuService;
import com.eghm.web.configuration.interceptor.PermInterceptor;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2018/1/30 09:30
 */
@RestController
@Api(tags = "菜单管理")
@AllArgsConstructor
@RequestMapping("/manage/menu")
public class MenuController {

    private final SysMenuService sysMenuService;

    private final PermInterceptor permInterceptor;

    @GetMapping("/list")
    @ApiOperation("菜单列表(不分页)")
    public List<MenuResponse> list() {
        return sysMenuService.getAuthList(SecurityHolder.getOperatorId());
    }

    @PostMapping("/create")
    @ApiOperation("添加菜单")
    public synchronized RespBody<Void> create(@Validated @RequestBody MenuAddRequest request) {
        sysMenuService.create(request);
        permInterceptor.refresh();
        return RespBody.success();
    }

    @PostMapping("/update")
    @ApiOperation("修改菜单")
    public RespBody<Void> update(@Validated @RequestBody MenuEditRequest request) {
        sysMenuService.update(request);
        permInterceptor.refresh();
        return RespBody.success();
    }

    @PostMapping("/updateState")
    @ApiOperation("更新菜单状态")
    public RespBody<Void> updateState(@Validated @RequestBody IdStateDTO dto) {
        sysMenuService.updateState(String.valueOf(dto.getId()), dto.getState());
        permInterceptor.refresh();
        return RespBody.success();
    }

    @PostMapping("/delete")
    @ApiOperation("删除菜单")
    public RespBody<Void> delete(@Validated @RequestBody IdDTO dto) {
        sysMenuService.delete(String.valueOf(dto.getId()));
        permInterceptor.refresh();
        return RespBody.success();
    }

}
