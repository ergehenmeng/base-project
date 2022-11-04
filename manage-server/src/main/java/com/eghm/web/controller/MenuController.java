package com.eghm.web.controller;

import com.eghm.model.SysMenu;
import com.eghm.model.dto.IdDTO;
import com.eghm.model.dto.IdStateDTO;
import com.eghm.model.dto.ext.RespBody;
import com.eghm.model.dto.menu.MenuAddRequest;
import com.eghm.model.dto.menu.MenuEditRequest;
import com.eghm.service.sys.SysMenuService;
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

    private final CustomFilterInvocationSecurityMetadataSource metadataSource;

    @GetMapping("/listPage")
    @ApiOperation("菜单列表(不分页)")
    public List<SysMenu> listPage() {
        return sysMenuService.getList();
    }

    @PostMapping("/create")
    @ApiOperation("添加菜单")
    public synchronized RespBody<Void> create(@Validated @RequestBody MenuAddRequest request) {
        sysMenuService.create(request);
        metadataSource.loadResource();
        return RespBody.success();
    }

    @PostMapping("/update")
    @ApiOperation("修改菜单")
    public RespBody<Void> update(@Validated @RequestBody MenuEditRequest request) {
        sysMenuService.update(request);
        metadataSource.loadResource();
        return RespBody.success();
    }

    @PostMapping("/updateState")
    @ApiOperation("更新菜单状态")
    public RespBody<Void> updateState(@Validated @RequestBody IdStateDTO dto) {
        sysMenuService.updateState(dto.getId(), dto.getState());
        metadataSource.loadResource();
        return RespBody.success();
    }

    @PostMapping("/delete")
    @ApiOperation("删除菜单")
    public RespBody<Void> delete(@Validated @RequestBody IdDTO dto) {
        sysMenuService.delete(dto.getId());
        metadataSource.loadResource();
        return RespBody.success();
    }

}
