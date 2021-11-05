package com.eghm.web.controller;

import cn.hutool.core.collection.CollUtil;
import com.eghm.common.constant.CacheConstant;
import com.eghm.configuration.security.SecurityOperator;
import com.eghm.configuration.security.SecurityOperatorHolder;
import com.eghm.dao.model.SysOperator;
import com.eghm.model.dto.ext.Paging;
import com.eghm.model.dto.ext.RespBody;
import com.eghm.model.dto.operator.*;
import com.eghm.service.cache.CacheService;
import com.eghm.service.sys.SysOperatorService;
import com.eghm.service.sys.SysRoleService;
import com.eghm.web.annotation.Mark;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Joiner;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

/**
 * @author 二哥很猛
 * @date 2018/11/26 17:10
 */
@RestController
@Api(tags = "系统用户管理")
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
    @Mark
    @ApiOperation("修改管理人员密码")
    public RespBody<Object> changePassword(HttpSession session, @Valid PasswordEditRequest request) {
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
    @GetMapping("/operator/list_page")
    @ApiOperation("管理后台用户列表")
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
    @Mark
    @ApiOperation("添加管理人员")
    public RespBody<Object> addOperator(@Valid OperatorAddRequest request) {
        sysOperatorService.addOperator(request);
        return RespBody.success();
    }

    /**
     * 管理人员编辑页面
     *
     * @param id 管理人员id
     * @return 页面
     */
    @GetMapping("/operator/{id}")
    @ApiOperation("查询系统用户信息")
    @ApiImplicitParam(name = "id", value = "id主键", required = true)
    public String editOperatorPage(Model model, @PathVariable("id") Long id) {
        SysOperator operator = sysOperatorService.getById(id);
        model.addAttribute("operator", operator);
        List<Long> roleList = sysRoleService.getByOperatorId(id);
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
    @Mark
    @ApiOperation("更新")
    public RespBody<Object> editOperator(@Valid OperatorEditRequest request) {
        sysOperatorService.updateOperator(request);
        return RespBody.success();
    }

    /**
     * 重置用户登录密码
     *
     * @return 成功
     */
    @PostMapping("/operator/reset_password")
    @Mark
    @ApiImplicitParam(name = "id", value = "id主键", required = true)
    @ApiOperation("重置用户登录密码")
    public RespBody<Object> resetPassword(Long id) {

        return RespBody.success();
    }

    /**
     * 锁屏操作
     */
    @PostMapping("/lock_screen")
    @Mark
    @ApiOperation("锁屏操作")
    public RespBody<Object> lockScreen() {
        SysOperator operator = SecurityOperatorHolder.getRequiredOperator();
        cacheService.setValue(CacheConstant.LOCK_SCREEN + operator.getId(), true);
        return RespBody.success();
    }

    /**
     * 解锁
     */
    @PostMapping("/unlock_screen")
    @Mark
    @ApiOperation("解锁操作")
    @ApiImplicitParam(name = "password", value = "密码", required = true)
    public RespBody<Object> unlockScreen(String password) {
        SysOperator operator = SecurityOperatorHolder.getRequiredOperator();
        sysOperatorService.checkPassword(password, operator.getPwd());
        cacheService.delete(CacheConstant.LOCK_SCREEN + operator.getId());
        return RespBody.success();
    }


    @PostMapping("/operator/handle")
    @Mark
    @ApiOperation("用户操作接口")
    public RespBody<Object> handle(@Valid OperatorHandleRequest request) {
        if (request.getState() == OperatorHandleRequest.LOCK) {
            sysOperatorService.lockOperator(request.getId());
        } else if (request.getState() == OperatorHandleRequest.UNLOCK) {
            sysOperatorService.unlockOperator(request.getId());
        } else if (request.getState() == OperatorHandleRequest.DELETE){
            sysOperatorService.deleteOperator(request.getId());
        } else {
            sysOperatorService.resetPassword(request.getId());
        }
        return RespBody.success();
    }

}
