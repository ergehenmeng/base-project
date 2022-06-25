package com.eghm.web.controller;

import com.eghm.common.enums.ErrorCode;
import com.eghm.configuration.security.CustomFilterInvocationSecurityMetadataSource;
import com.eghm.dao.model.SysMenu;
import com.eghm.model.dto.IdDTO;
import com.eghm.model.dto.ext.RespBody;
import com.eghm.model.dto.menu.MenuAddRequest;
import com.eghm.model.dto.menu.MenuEditRequest;
import com.eghm.service.sys.SysMenuService;
import com.eghm.web.annotation.Mark;
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
@RequestMapping("/menu")
public class MenuController {

    private final SysMenuService sysMenuService;

    private final CustomFilterInvocationSecurityMetadataSource metadataSource;

    @GetMapping("/listPage")
    @ApiOperation("菜单列表(不分页)")
    public RespBody<List<SysMenu>> listPage() {
        List<SysMenu> menuList = sysMenuService.getList();
        return RespBody.success(menuList);
    }

    @PostMapping("/create")
    @Mark
    @ApiOperation("添加菜单")
    public synchronized RespBody<Void> create(@Validated @RequestBody MenuAddRequest request) {
        if (request.getGrade() > SysMenu.BUTTON) {
            return RespBody.error(ErrorCode.SUB_MENU_ERROR);
        }
        sysMenuService.create(request);
        metadataSource.loadResource();
        return RespBody.success();
    }

    @PostMapping("/update")
    @Mark
    @ApiOperation("修改菜单")
    public RespBody<Void> update(@Validated @RequestBody MenuEditRequest request) {
        sysMenuService.update(request);
        metadataSource.loadResource();
        return RespBody.success();
    }

    @PostMapping("/delete")
    @Mark
    @ApiOperation("删除菜单")
    public RespBody<Void> delete(@Validated @RequestBody IdDTO dto) {
        sysMenuService.delete(dto.getId());
        metadataSource.loadResource();
        return RespBody.success();
    }

}
