package com.fanyin.controller.system;

import com.fanyin.annotation.Mark;
import com.fanyin.annotation.RequestType;
import com.fanyin.controller.AbstractController;
import com.fanyin.dto.system.menu.MenuAddRequest;
import com.fanyin.dto.system.menu.MenuEditRequest;
import com.fanyin.ext.RespBody;
import com.fanyin.model.system.SystemMenu;
import com.fanyin.model.system.SystemOperator;
import com.fanyin.service.system.SystemMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
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

    /**
     * 菜单编辑页面
     * @param model model存放对象
     * @param id 菜单主键
     * @return 页面地址
     */
    @PostMapping("/public/system/menu/edit_page")
    @Mark(RequestType.PAGE)
    public String editMenuPage(Model model, Integer id){
        SystemMenu menu = systemMenuService.getMenuById(id);
        model.addAttribute("menu",menu);
        return "public/system/menu/edit_page";
    }

    /**
     * 获取所有可用的菜单列表,注意不分页
     * @return list
     */
    @PostMapping("/system/menu/list_page")
    @ResponseBody
    @Mark(RequestType.SELECT)
    public List<SystemMenu> listPage(){
        return systemMenuService.getAllList();
    }

    /**
     * 菜单添加页面
     * @param model model
     * @param nid 父节点nid
     * @param id 父级id
     * @return ftl地址
     */
    @PostMapping("/public/system/menu/add_page")
    @Mark(RequestType.ALL)
    public String addPage(Model model,String nid,Integer id){
        model.addAttribute("nid",nid);
        model.addAttribute("pid",id);
        return "public/system/menu/add_page";
    }

    /**
     * 新增菜单
     * @param request 请求参数组装
     * @return 成功状态
     */
    @PostMapping("/system/menu/add")
    @ResponseBody
    @Mark(RequestType.INSERT)
    public RespBody add(MenuAddRequest request){
        systemMenuService.addMenu(request);
        return RespBody.getInstance();
    }

    /**
     * 更新菜单信息
     * @param request 菜单信息
     * @return 成功返回值
     */
    @PostMapping("/system/menu/edit")
    @ResponseBody
    @Mark(RequestType.UPDATE)
    public RespBody edit(MenuEditRequest request){
        systemMenuService.updateMenu(request);
        return RespBody.getInstance();
    }

    /**
     * 根据主键删除菜单
     * @param id 主键
     * @return 成功后的返回信息
     */
    @PostMapping("/system/menu/delete")
    @Mark(RequestType.DELETE)
    public RespBody delete(Integer id){
        systemMenuService.deleteMenu(id);
        return RespBody.getInstance();
    }

    /**
     * 查询管理人员自己所拥有的菜单
     * @return 菜单列表
     */
    @PostMapping("/system/operator/menu_list")
    @ResponseBody
    @Mark(RequestType.SELECT)
    public List<SystemMenu> operatorMenuList(){
        SystemOperator operator = getRequiredOperator();
        return systemMenuService.getMenuList(operator.getId(),null);
    }

}
