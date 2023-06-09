package com.eghm.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.constant.CacheConstant;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.model.SysUser;
import com.eghm.dto.IdDTO;
import com.eghm.dto.ext.JwtUser;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.dto.user.CheckPwdRequest;
import com.eghm.dto.user.UserAddRequest;
import com.eghm.dto.user.UserEditRequest;
import com.eghm.dto.user.UserQueryRequest;
import com.eghm.dto.user.PasswordEditRequest;
import com.eghm.vo.menu.MenuResponse;
import com.eghm.vo.user.UserResponse;
import com.eghm.service.cache.CacheService;
import com.eghm.service.sys.SysMenuService;
import com.eghm.service.sys.SysUserService;
import com.eghm.service.sys.SysRoleService;
import com.eghm.utils.DataUtil;
import com.google.common.base.Joiner;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2018/11/26 17:10
 */
@RestController
@Api(tags = "系统用户管理")
@AllArgsConstructor
@RequestMapping("/manage/user")
public class UserController {

    private final SysUserService sysUserService;

    private final SysRoleService sysRoleService;

    private final CacheService cacheService;

    private final SysMenuService sysMenuService;

    @PostMapping("/changePwd")
    @ApiOperation("修改管理人员密码")
    public RespBody<Void> changePwd(@Validated @RequestBody PasswordEditRequest request) {
        request.setUserId(SecurityHolder.getUserId());
        sysUserService.updateLoginPassword(request);
        return RespBody.success();
    }

    @GetMapping("/listPage")
    @ApiOperation("管理后台用户列表")
    public PageData<SysUser> listPage(UserQueryRequest request) {
        Page<SysUser> page = sysUserService.getByPage(request);
        return PageData.toPage(page);
    }

    @PostMapping("/create")
    @ApiOperation("添加管理人员")
    public RespBody<Void> create(@Validated @RequestBody UserAddRequest request) {
        sysUserService.create(request);
        return RespBody.success();
    }

    @GetMapping("/select")
    @ApiOperation("查询系统用户信息")
    @ApiImplicitParam(name = "id", value = "id主键", required = true)
    public UserResponse select(@RequestParam("id") Long id) {
        SysUser user = sysUserService.getById(id);
        UserResponse response = DataUtil.copy(user, UserResponse.class);
        List<Long> roleList = sysRoleService.getByUserId(id);
        response.setRoleIds(Joiner.on(",").join(roleList));
        return response;
    }

    @PostMapping("/update")
    @ApiOperation("系统用户信息")
    public RespBody<Void> update(@Validated @RequestBody UserEditRequest request) {
        sysUserService.update(request);
        return RespBody.success();
    }

    @PostMapping("/lockScreen")
    @ApiOperation("锁屏操作")
    public RespBody<Void> lockScreen() {
        JwtUser user = SecurityHolder.getOperatorRequired();
        cacheService.setValue(CacheConstant.LOCK_SCREEN + user.getId(), true);
        return RespBody.success();
    }

    @PostMapping("/unlockScreen")
    @ApiOperation("解锁操作")
    @ApiImplicitParam(name = "password", value = "密码", required = true)
    public RespBody<Void> unlockScreen(@RequestBody @Validated CheckPwdRequest request) {
        JwtUser user = SecurityHolder.getOperatorRequired();
        // TODO 临时密码
        sysUserService.checkPassword(request.getPwd(), "");
        cacheService.delete(CacheConstant.LOCK_SCREEN + user.getId());
        return RespBody.success();
    }

    @PostMapping("/lock")
    @ApiOperation("锁定用户")
    public RespBody<Void> lock(@Validated @RequestBody IdDTO request) {
        sysUserService.lockOperator(request.getId());
        return RespBody.success();
    }

    @PostMapping("/unlock")
    @ApiOperation("解锁用户")
    public RespBody<Void> unlock(@Validated @RequestBody IdDTO request) {
        sysUserService.unlockOperator(request.getId());
        return RespBody.success();
    }

    @PostMapping("/delete")
    @ApiOperation("删除用户")
    public RespBody<Void> delete(@Validated @RequestBody IdDTO request) {
        sysUserService.delete(request.getId());
        return RespBody.success();
    }

    @PostMapping("/reset")
    @ApiOperation("重置密码")
    public RespBody<Void> reset(@Validated @RequestBody IdDTO request) {
        sysUserService.resetPassword(request.getId());
        return RespBody.success();
    }

    @GetMapping("/menuList")
    @ApiOperation("查询自己拥有的菜单列表")
    public List<MenuResponse> menuList() {
        return sysMenuService.getList(SecurityHolder.getUserId());
    }
}
