package com.eghm.web.controller;

import com.eghm.common.enums.ErrorCode;
import com.eghm.configuration.security.CustomFilterInvocationSecurityMetadataSource;
import com.eghm.dao.model.SysMenu;
import com.eghm.model.dto.ext.RespBody;
import com.eghm.model.dto.menu.MenuAddRequest;
import com.eghm.model.dto.menu.MenuEditRequest;
import com.eghm.service.sys.SysMenuService;
import com.eghm.web.annotation.Mark;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    /**
     * 获取所有可用的菜单列表,注意不分页
     *
     * @return list
     */
    @GetMapping("/listPage")
    @ApiOperation("菜单列表(不分页)")
    public List<SysMenu> listPage() {
        return sysMenuService.getAllList();
    }

    /**
     * 新增菜单
     *
     * @param request 请求参数组装
     * @return 成功状态
     */
    @PostMapping("/add")
    @Mark
    @ApiOperation("添加菜单")
    public synchronized RespBody<Void> add(@Valid MenuAddRequest request) {
        if (request.getGrade() > SysMenu.BUTTON) {
            return RespBody.error(ErrorCode.SUB_MENU_ERROR);
        }
        sysMenuService.addMenu(request);
        metadataSource.loadResource();
        return RespBody.success();
    }

    /**
     * 更新菜单信息
     *
     * @param request 菜单信息
     * @return 成功返回值
     */
    @PostMapping("/edit")
    @Mark
    @ApiOperation("修改菜单")
    public RespBody<Void> edit(@Valid MenuEditRequest request) {
        sysMenuService.updateMenu(request);
        metadataSource.loadResource();
        return RespBody.success();
    }

    /**
     * 根据主键删除菜单
     *
     * @param id 主键
     * @return 成功后的返回信息
     */
    @PostMapping("/delete")
    @Mark
    @ApiOperation("删除菜单")
    @ApiImplicitParam(name = "id", value = "id", required = true)
    public RespBody<Void> delete(@RequestParam("id") Long id) {
        sysMenuService.deleteMenu(id);
        metadataSource.loadResource();
        return RespBody.success();
    }

}
