package com.eghm.web.controller.sys;

import com.eghm.configuration.security.SecurityHolder;
import com.eghm.dto.IdDTO;
import com.eghm.dto.SortByDTO;
import com.eghm.dto.ext.RespBody;
import com.eghm.dto.sys.menu.MenuAddRequest;
import com.eghm.dto.sys.menu.MenuEditRequest;
import com.eghm.dto.sys.menu.MenuQueryRequest;
import com.eghm.dto.poi.StateRequest;
import com.eghm.service.sys.SysMenuService;
import com.eghm.vo.menu.MenuFullResponse;
import com.eghm.vo.menu.MenuResponse;
import com.eghm.web.configuration.interceptor.PermInterceptor;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
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
@Tag(name="菜单管理")
@AllArgsConstructor
@RequestMapping(value = "/manage/menu", produces = MediaType.APPLICATION_JSON_VALUE)
public class MenuController {

    private final SysMenuService sysMenuService;

    private final PermInterceptor permInterceptor;

    @GetMapping("/list")
    @Operation(summary = "全部菜单")
    public RespBody<List<MenuFullResponse>> list(MenuQueryRequest request) {
        List<MenuFullResponse> responseList = sysMenuService.getList(request);
        return RespBody.success(responseList);
    }

    @GetMapping("/systemList")
    @Operation(summary = "管理员菜单")
    public RespBody<List<MenuResponse>> systemList() {
        List<MenuResponse> responseList = sysMenuService.getSystemList();
        return RespBody.success(responseList);
    }

    @GetMapping("/merchantList")
    @Operation(summary = "商户菜单")
    public RespBody<List<MenuResponse>> merchantList() {
        List<MenuResponse> responseList = sysMenuService.getMerchantList(SecurityHolder.getUserId());
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
