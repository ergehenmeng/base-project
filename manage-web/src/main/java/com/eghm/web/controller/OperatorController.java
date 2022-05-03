package com.eghm.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.common.constant.CacheConstant;
import com.eghm.configuration.security.SecurityOperator;
import com.eghm.configuration.security.SecurityOperatorHolder;
import com.eghm.dao.model.SysMenu;
import com.eghm.dao.model.SysOperator;
import com.eghm.model.dto.ext.Paging;
import com.eghm.model.dto.ext.RespBody;
import com.eghm.model.dto.operator.*;
import com.eghm.model.vo.operator.OperatorResponse;
import com.eghm.service.cache.CacheService;
import com.eghm.service.sys.SysMenuService;
import com.eghm.service.sys.SysOperatorService;
import com.eghm.service.sys.SysRoleService;
import com.eghm.utils.DataUtil;
import com.eghm.web.annotation.Mark;
import com.google.common.base.Joiner;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

/**
 * @author 二哥很猛
 * @date 2018/11/26 17:10
 */
@RestController
@Api(tags = "系统用户管理")
@AllArgsConstructor
@RequestMapping("/operator")
public class OperatorController {

    private final SysOperatorService sysOperatorService;

    private final SysRoleService sysRoleService;

    private final CacheService cacheService;

    private final SysMenuService sysMenuService;

    /**
     * 修改密码
     *
     * @param request 请求参数
     * @return 成功状态
     */
    @PostMapping("/changePwd")
    @Mark
    @ApiOperation("修改管理人员密码")
    public RespBody<Void> changePwd(HttpSession session, @Valid PasswordEditRequest request) {
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
    @GetMapping("/listPage")
    @ApiOperation("管理后台用户列表")
    public Paging<SysOperator> operatorListPage(OperatorQueryRequest request) {
        Page<SysOperator> page = sysOperatorService.getByPage(request);
        return new Paging<>(page);
    }


    /**
     * 添加管理人员
     *
     * @return 成功
     */
    @PostMapping("/add")
    @Mark
    @ApiOperation("添加管理人员")
    public RespBody<Void> addOperator(@Valid OperatorAddRequest request) {
        sysOperatorService.addOperator(request);
        return RespBody.success();
    }

    /**
     * 管理人员编辑页面
     *
     * @param id 管理人员id
     * @return 页面
     */
    @GetMapping("/select")
    @ApiOperation("查询系统用户信息")
    @ApiImplicitParam(name = "id", value = "id主键", required = true)
    public RespBody<OperatorResponse> select(@RequestParam("id") Long id) {
        SysOperator operator = sysOperatorService.getById(id);
        OperatorResponse response = DataUtil.copy(operator, OperatorResponse.class);
        List<Long> roleList = sysRoleService.getByOperatorId(id);
        response.setRoleIds(Joiner.on(",").join(roleList));
        return RespBody.success(response);
    }

    /**
     * 更新管理人员信息
     *
     * @param request 前台参数
     * @return 成功
     */
    @PostMapping("/edit")
    @Mark
    @ApiOperation("系统用户信息")
    public RespBody<Void> editOperator(@Valid OperatorEditRequest request) {
        sysOperatorService.updateOperator(request);
        return RespBody.success();
    }


    /**
     * 锁屏操作
     */
    @PostMapping("/lockScreen")
    @Mark
    @ApiOperation("锁屏操作")
    public RespBody<Void> lockScreen() {
        SysOperator operator = SecurityOperatorHolder.getRequiredOperator();
        cacheService.setValue(CacheConstant.LOCK_SCREEN + operator.getId(), true);
        return RespBody.success();
    }

    /**
     * 解锁
     */
    @PostMapping("/unlockScreen")
    @Mark
    @ApiOperation("解锁操作")
    @ApiImplicitParam(name = "password", value = "密码", required = true)
    public RespBody<Void> unlockScreen(String password) {
        SysOperator operator = SecurityOperatorHolder.getRequiredOperator();
        sysOperatorService.checkPassword(password, operator.getPwd());
        cacheService.delete(CacheConstant.LOCK_SCREEN + operator.getId());
        return RespBody.success();
    }


    @PostMapping("/handle")
    @Mark
    @ApiOperation("用户操作(锁定,解锁,删除,重置密码)")
    public RespBody<Void> handle(@Valid OperatorHandleRequest request) {
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

    /**
     * 查询管理人员自己所拥有的菜单
     *
     * @return 菜单列表
     */
    @GetMapping("/menuList")
    @ApiOperation("查询自己拥有的菜单列表")
    public List<SysMenu> operatorMenuList() {
        SysOperator operator = SecurityOperatorHolder.getRequiredOperator();
        return sysMenuService.getList(operator.getId());
    }
}
