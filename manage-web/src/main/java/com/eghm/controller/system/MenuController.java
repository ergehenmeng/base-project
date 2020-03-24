package com.eghm.controller.system;

import com.eghm.annotation.Mark;
import com.eghm.common.enums.ErrorCode;
import com.eghm.configuration.security.CustomFilterInvocationSecurityMetadataSource;
import com.eghm.controller.AbstractController;
import com.eghm.model.dto.system.menu.MenuAddRequest;
import com.eghm.model.dto.system.menu.MenuEditRequest;
import com.eghm.model.ext.RespBody;
import com.eghm.dao.model.system.SystemMenu;
import com.eghm.dao.model.system.SystemOperator;
import com.eghm.service.system.SystemMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2018/1/30 09:30
 */
@Controller
public class MenuController extends AbstractController {

    @Autowired
    private SystemMenuService systemMenuService;

    @Autowired
    private CustomFilterInvocationSecurityMetadataSource metadataSource;

    /**
     * 菜单编辑页面
     *
     * @param model model存放对象
     * @param id    菜单主键
     * @return 页面地址
     */
    @GetMapping("/system/menu/edit_page")
    @Mark
    public String editMenuPage(Model model, Integer id) {
        SystemMenu menu = systemMenuService.getMenuById(id);
        model.addAttribute("menu", menu);
        return "system/menu/edit_page";
    }

    /**
     * 获取所有可用的菜单列表,注意不分页
     *
     * @return list
     */
    @PostMapping("/system/menu/list_page")
    @ResponseBody
    @Mark
    public List<SystemMenu> listPage() {
        return systemMenuService.getAllList();
    }

    /**
     * 菜单添加页面
     *
     * @param model model
     * @param id    父级id
     * @return ftl地址
     */
    @GetMapping("/system/menu/add_page")
    @Mark
    public String addPage(Model model, Integer id, @RequestParam(defaultValue = "0", required = false) Byte grade) {
        model.addAttribute("pid", id);
        model.addAttribute("grade", grade + 1);
        return "system/menu/add_page";
    }

    /**
     * 新增菜单
     *
     * @param request 请求参数组装
     * @return 成功状态
     */
    @PostMapping("/system/menu/add")
    @ResponseBody
    @Mark
    public RespBody<Object> add(MenuAddRequest request) {
        if (request.getGrade() > SystemMenu.BUTTON) {
            return RespBody.error(ErrorCode.SUB_MENU_ERROR);
        }
        systemMenuService.addMenu(request);
        metadataSource.refreshResource();
        return RespBody.success();
    }

    /**
     * 更新菜单信息
     *
     * @param request 菜单信息
     * @return 成功返回值
     */
    @PostMapping("/system/menu/edit")
    @ResponseBody
    @Mark
    public RespBody<Object> edit(MenuEditRequest request) {
        systemMenuService.updateMenu(request);
        metadataSource.refreshResource();
        return RespBody.success();
    }

    /**
     * 根据主键删除菜单
     *
     * @param id 主键
     * @return 成功后的返回信息
     */
    @PostMapping("/system/menu/delete")
    @Mark
    @ResponseBody
    public RespBody<Object> delete(Integer id) {
        systemMenuService.deleteMenu(id);
        metadataSource.refreshResource();
        return RespBody.success();
    }

    /**
     * 查询管理人员自己所拥有的菜单
     *
     * @return 菜单列表
     */
    @PostMapping("/system/operator/menu_list")
    @ResponseBody
    @Mark
    public List<SystemMenu> operatorMenuList() {
        SystemOperator operator = getRequiredOperator();
        return systemMenuService.getList(operator.getId());
    }

}
