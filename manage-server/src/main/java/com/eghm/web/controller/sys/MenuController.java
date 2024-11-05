package com.eghm.web.controller.sys;

import com.eghm.dto.IdDTO;
import com.eghm.dto.SortByDTO;
import com.eghm.dto.ext.RespBody;
import com.eghm.dto.menu.MenuAddRequest;
import com.eghm.dto.menu.MenuEditRequest;
import com.eghm.dto.menu.MenuQueryRequest;
import com.eghm.service.sys.SysMenuService;
import com.eghm.vo.menu.MenuFullResponse;
import com.eghm.vo.menu.MenuResponse;
import com.eghm.web.configuration.interceptor.PermInterceptor;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2018/1/30 09:30
 */
@RestController
@Api(tags = "菜单管理")
@AllArgsConstructor
@RequestMapping(value = "/manage/menu", produces = MediaType.APPLICATION_JSON_VALUE)
public class MenuController {

    private final SysMenuService sysMenuService;

    private final PermInterceptor permInterceptor;

    @GetMapping("/list")
    @ApiOperation("全部菜单")
    public RespBody<List<MenuFullResponse>> list(MenuQueryRequest request) {
        List<MenuFullResponse> responseList = sysMenuService.getList(request);
        return RespBody.success(responseList);
    }

    @GetMapping("/systemList")
    @ApiOperation("管理员菜单")
    public RespBody<List<MenuResponse>> systemList() {
        List<MenuResponse> responseList = sysMenuService.getSystemList();
        return RespBody.success(responseList);
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("添加菜单")
    public synchronized RespBody<Void> create(@Validated @RequestBody MenuAddRequest request) {
        sysMenuService.create(request);
        permInterceptor.refresh();
        return RespBody.success();
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("修改菜单")
    public RespBody<Void> update(@Validated @RequestBody MenuEditRequest request) {
        sysMenuService.update(request);
        permInterceptor.refresh();
        return RespBody.success();
    }

    @PostMapping(value = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("删除菜单")
    public RespBody<Void> delete(@Validated @RequestBody IdDTO dto) {
        sysMenuService.delete(String.valueOf(dto.getId()));
        permInterceptor.refresh();
        return RespBody.success();
    }

    @PostMapping(value = "/sort", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("更新排序")
    public RespBody<Void> sort(@RequestBody @Validated SortByDTO dto) {
        sysMenuService.sortBy(String.valueOf(dto.getId()), dto.getSortBy());
        return RespBody.success();
    }

    @PostMapping(value = "/updateState", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("更新状态")
    public RespBody<Void> updateState(@Validated @RequestBody SortByDTO.StateRequest request) {
        sysMenuService.updateState(String.valueOf(request.getId()), request.getState());
        permInterceptor.refresh();
        return RespBody.success();
    }

}
