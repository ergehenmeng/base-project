package com.eghm.web.controller;

import com.eghm.common.constant.CacheConstant;
import com.eghm.common.constant.CommonConstant;
import com.eghm.configuration.security.SecurityOperatorHolder;
import com.eghm.dao.model.SysOperator;
import com.eghm.service.cache.CacheService;
import com.eghm.service.sys.SysDataDeptService;
import com.eghm.service.sys.SysMenuService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * 首页及登陆
 *
 * @author 二哥很猛
 * @date 2018/1/8 14:41
 */
@RestController
@AllArgsConstructor
public class CommonController {

    private final SysMenuService sysMenuService;

    private final CacheService cacheService;

    private final SysDataDeptService sysDataDeptService;

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
        SysOperator operator = SecurityOperatorHolder.getRequiredOperator();
        //导航菜单
        if (operator.getLeftMenu() == null) {
            operator.setLeftMenu(sysMenuService.getMenuList(operator.getId()));
        }
        //按钮菜单
        if (operator.getButtonMenu() == null) {
            operator.setButtonMenu(sysMenuService.getButtonList(operator.getId()));
        }
        // 数据权限
        if (operator.getDeptList() == null) {
            operator.setDeptList(sysDataDeptService.getDeptList(operator.getId()));
        }
        model.addAttribute("menuList", operator.getLeftMenu());
        model.addAttribute("userName", operator.getOperatorName());
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
     * @param function 所属子模块
     * @param page     页面名称
     * @return 对应的页面
     */
    @GetMapping("/{function}/{page}")
    public String modules(@PathVariable("function") String function, @PathVariable("page") String page) {
        return function + CommonConstant.URL_SEPARATOR + page;
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
