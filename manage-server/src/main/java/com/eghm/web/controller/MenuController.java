package com.eghm.web.controller;

import com.eghm.dto.IdDTO;
import com.eghm.dto.ext.RespBody;
import com.eghm.dto.menu.MenuAddRequest;
import com.eghm.dto.menu.MenuEditRequest;
import com.eghm.service.sys.SysMenuService;
import com.eghm.vo.menu.MenuResponse;
import com.eghm.web.configuration.interceptor.PermInterceptor;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    @ApiOperation("全部菜单")
    public RespBody<List<MenuResponse>> list() {
        List<MenuResponse> responseList = sysMenuService.getList();
        return RespBody.success(responseList);
    }

    @GetMapping("/systemList")
    @ApiOperation("菜单列表(系统菜单)")
    public RespBody<List<MenuResponse>> systemList() {
        List<MenuResponse> responseList = sysMenuService.getSystemList();
        return RespBody.success(responseList);
    }

    @GetMapping("/merchantList")
    @ApiOperation("菜单列表(不分页)")
    public RespBody<List<MenuResponse>> merchantList() {
        List<MenuResponse> responseList = sysMenuService.getMerchantList();
        return RespBody.success(responseList);
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

    @PostMapping("/delete")
    @ApiOperation("删除菜单")
    public RespBody<Void> delete(@Validated @RequestBody IdDTO dto) {
        sysMenuService.delete(String.valueOf(dto.getId()));
        permInterceptor.refresh();
        return RespBody.success();
    }

}
