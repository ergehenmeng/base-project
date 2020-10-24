package com.eghm.web.controller;

import cn.hutool.core.collection.CollUtil;
import com.eghm.common.constant.CacheConstant;
import com.eghm.configuration.security.SecurityOperator;
import com.eghm.configuration.security.SecurityOperatorHolder;
import com.eghm.dao.model.SysOperator;
import com.eghm.model.dto.operator.OperatorAddRequest;
import com.eghm.model.dto.operator.OperatorEditRequest;
import com.eghm.model.dto.operator.OperatorQueryRequest;
import com.eghm.model.dto.operator.PasswordEditRequest;
import com.eghm.model.dto.ext.Paging;
import com.eghm.model.dto.ext.RespBody;
import com.eghm.service.cache.CacheService;
import com.eghm.service.sys.SysOperatorService;
import com.eghm.service.sys.SysRoleService;
import com.eghm.web.annotation.Mark;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Joiner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author 二哥很猛
 * @date 2018/11/26 17:10
 */
@Controller
public class OperatorController {

    private SysOperatorService sysOperatorService;

    private SysRoleService sysRoleService;

    private CacheService cacheService;

    @Autowired
    public void setSysRoleService(SysRoleService sysRoleService) {
        this.sysRoleService = sysRoleService;
    }

    @Autowired
    public void setSysOperatorService(SysOperatorService sysOperatorService) {
        this.sysOperatorService = sysOperatorService;
    }

    @Autowired
    public void setCacheService(CacheService cacheService) {
        this.cacheService = cacheService;
    }

    /**
     * 修改密码
     *
     * @param request 请求参数
     * @return 成功状态
     */
    @PostMapping("/operator/change_password")
    @ResponseBody
    @Mark
    public RespBody<Object> changePassword(HttpSession session, PasswordEditRequest request) {
        SecurityOperator operator = SecurityOperatorHolder.getRequiredOperator();
        request.setOperatorId(operator.getId());
        String newPassword = sysOperatorService.updateLoginPassword(request);
        operator.setPwd(newPassword);
        //更新用户权限
        SecurityContext context = (SecurityContext) session.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
        Authentication authentication = context.getAuthentication();
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(operator, authentication, operator.getAuthorities());
        token.setDetails(authentication.getDetails());
        context.setAuthentication(token);
        return RespBody.success();
    }

    /**
     * 分页查询系统操作人员列表
     *
     * @param request 查询条件
     * @return 列表
     */
    @PostMapping("/operator/list_page")
    @ResponseBody
    @Mark
    public Paging<SysOperator> operatorListPage(OperatorQueryRequest request) {
        PageInfo<SysOperator> page = sysOperatorService.getByPage(request);
        return new Paging<>(page);
    }


    /**
     * 添加管理人员
     *
     * @return 成功
     */
    @PostMapping("/operator/add")
    @ResponseBody
    @Mark
    public RespBody<Object> addOperator(OperatorAddRequest request) {
        sysOperatorService.addOperator(request);
        return RespBody.success();
    }

    /**
     * 管理人员编辑页面
     *
     * @param id 管理人员id
     * @return 页面
     */
    @GetMapping("/operator/edit_page")
    @Mark
    public String editOperatorPage(Model model, Integer id) {
        SysOperator operator = sysOperatorService.getById(id);
        model.addAttribute("operator", operator);
        List<Integer> roleList = sysRoleService.getByOperatorId(id);
        if (CollUtil.isNotEmpty(roleList)) {
            String roleIds = Joiner.on(",").join(roleList);
            model.addAttribute("roleIds", roleIds);
        }
        return "operator/edit_page";
    }

    /**
     * 更新管理人员信息
     *
     * @param request 前台参数
     * @return 成功
     */
    @PostMapping("/operator/edit")
    @ResponseBody
    @Mark
    public RespBody<Object> editOperator(OperatorEditRequest request) {
        sysOperatorService.updateOperator(request);
        return RespBody.success();
    }

    /**
     * 重置用户登录密码
     *
     * @return 成功
     */
    @PostMapping("/operator/reset_password")
    @ResponseBody
    @Mark
    public RespBody<Object> resetPassword(Integer id) {
        sysOperatorService.resetPassword(id);
        return RespBody.success();
    }

    /**
     * 锁屏操作
     */
    @PostMapping("/lock_screen")
    @ResponseBody
    public RespBody<Object> lockScreen() {
        SysOperator operator = SecurityOperatorHolder.getRequiredOperator();
        cacheService.setValue(CacheConstant.LOCK_SCREEN + operator.getId(), true);
        return RespBody.success();
    }

    /**
     * 解锁
     */
    @PostMapping("/unlock_screen")
    @ResponseBody
    public RespBody<Object> unlockScreen(String password) {
        SysOperator operator = SecurityOperatorHolder.getRequiredOperator();
        sysOperatorService.checkPassword(password, operator.getPwd());
        cacheService.delete(CacheConstant.LOCK_SCREEN + operator.getId());
        return RespBody.success();
    }

    /**
     * 锁定用户
     */
    @PostMapping("/operator/lock_operator")
    @ResponseBody
    public RespBody<Object> lockOperator(Integer id) {
        sysOperatorService.lockOperator(id);
        return RespBody.success();
    }

    /**
     * 解锁用户
     */
    @PostMapping("/operator/unlock_operator")
    @ResponseBody
    public RespBody<Object> unlock(Integer id) {
        sysOperatorService.unlockOperator(id);
        return RespBody.success();
    }

    /**
     * 删除用户
     */
    @PostMapping("/operator/delete")
    @ResponseBody
    public RespBody<Object> delete(Integer id) {
        sysOperatorService.deleteOperator(id);
        return RespBody.success();
    }
}
