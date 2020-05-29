package com.eghm.controller;

import com.eghm.common.constant.CacheConstant;
import com.eghm.dao.model.system.SystemOperator;
import com.eghm.service.cache.CacheService;
import com.eghm.service.system.SystemMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 首页及登陆
 *
 * @author 二哥很猛
 * @date 2018/1/8 14:41
 */
@Controller
public class CommonController extends AbstractController {

    @Autowired
    private SystemMenuService systemMenuService;

    @Autowired
    private CacheService cacheService;

    /**
     * 未登录的首页
     *
     * @return 首页index.ftl
     */
    @GetMapping(value = {"/", "/index"})
    public String index() {
        return "index";
    }

    /**
     * 登陆后的首页,注意如果权限重新分配,需重新登陆系统
     *
     * @return home页面
     */
    @GetMapping("/main")
    public String home(Model model) {
        SystemOperator operator = getRequiredOperator();
        //导航菜单
        if (operator.getLeftMenu() == null) {
            operator.setLeftMenu(systemMenuService.getMenuList(operator.getId()));
        }
        //按钮菜单
        if (operator.getButtonMenu() == null) {
            operator.setButtonMenu(systemMenuService.getButtonList(operator.getId()));
        }
        model.addAttribute("menuList", operator.getLeftMenu());
        model.addAttribute("isInit", operator.getPwd().equals(operator.getInitPwd()));
        model.addAttribute("isLock", cacheService.exist(CacheConstant.LOCK_SCREEN + operator.getId()));
        return "main/home";
    }



    /**
     * 首页门户框
     *
     * @return 门户页面
     */
    @GetMapping("/portal")
    public String portal() {
        return "portal";
    }

    /**
     * 全局页面定向,由于模糊映射可能会导致js,css,img也会映射到此处,因此在静态资源文件目录结构必须保证4层结构
     *
     * @param modules  所属主模块
     * @param function 所属子模块
     * @param page     页面名称
     * @return 对应的页面
     */
    @GetMapping("/{modules}/{function}/{page}")
    public String modules(@PathVariable("modules") String modules, @PathVariable("function") String function, @PathVariable("page") String page) {
        return modules + "/" + function + "/" + page;
    }

    /**
     * 首页修改密码
     *
     * @return 页面
     */
    @GetMapping("/main/change_password_page")
    public String changePasswordPage() {
        return "main/change_password_page";
    }

    /**
     * 角色列表展示
     */
    @GetMapping("/main/role_menu_page")
    public String roleMenuPage() {
        return "main/role_menu_page";
    }
}
