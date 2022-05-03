package com.eghm.web.controller;

import com.eghm.common.enums.ErrorCode;
import com.eghm.configuration.security.CustomFilterInvocationSecurityMetadataSource;
import com.eghm.configuration.security.SecurityOperatorHolder;
import com.eghm.dao.model.SysMenu;
import com.eghm.dao.model.SysOperator;
import com.eghm.model.dto.ext.RespBody;
import com.eghm.model.dto.menu.MenuAddRequest;
import com.eghm.model.dto.menu.MenuEditRequest;
import com.eghm.service.sys.SysMenuService;
import com.eghm.web.annotation.Mark;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @author 二哥很猛
 * @date 2018/1/30 09:30
 */
@RestController
@Api(tags = "菜单管理")
@AllArgsConstructor
public class MenuController {

    private final SysMenuService sysMenuService;

    private final CustomFilterInvocationSecurityMetadataSource metadataSource;

    /**
     * 菜单编辑页面
     *
     * @param model model存放对象
     * @param id    菜单主键
     * @return 页面地址
     */
    @GetMapping("/menu/edit_page")
    public String editMenuPage(Model model, Long id) {
        SysMenu menu = sysMenuService.getMenuById(id);
        model.addAttribute("menu", menu);
        return "menu/edit_page";
    }

    /**
     * 获取所有可用的菜单列表,注意不分页
     *
     * @return list
     */
    @GetMapping("/menu/list_page")
    @ApiOperation("菜单列表(不分页)")
    public List<SysMenu> listPage() {
        return sysMenuService.getAllList();
    }

    /**
     * 菜单添加页面
     *
     * @param model model
     * @param id    父级id
     * @return ftl地址
     */
    @GetMapping("/menu/add_page")
    public String addPage(Model model, Long id, @RequestParam(defaultValue = "0", required = false) Byte grade) {
        model.addAttribute("pid", id);
        model.addAttribute("grade", grade + 1);
        return "menu/add_page";
    }

    /**
     * 新增菜单
     *
     * @param request 请求参数组装
     * @return 成功状态
     */
    @PostMapping("/menu/add")
    @Mark
    @ApiOperation("添加菜单")
    public synchronized RespBody<Object> add(@Valid MenuAddRequest request) {
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
    @PostMapping("/menu/edit")
    @Mark
    @ApiOperation("修改菜单")
    public RespBody<Object> edit(@Valid MenuEditRequest request) {
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
    @PostMapping("/menu/delete")
    @Mark
    @ApiOperation("删除菜单")
    @ApiImplicitParam(name = "id", value = "id", required = true)
    public RespBody<Object> delete(@RequestParam("id") Long id) {
        sysMenuService.deleteMenu(id);
        metadataSource.loadResource();
        return RespBody.success();
    }

    /**
     * 查询管理人员自己所拥有的菜单
     *
     * @return 菜单列表
     */
    @GetMapping("/operator/menu_list")
    @ApiOperation("查询自己拥有的菜单列表")
    public List<SysMenu> operatorMenuList() {
        SysOperator operator = SecurityOperatorHolder.getRequiredOperator();
        return sysMenuService.getList(operator.getId());
    }

}
