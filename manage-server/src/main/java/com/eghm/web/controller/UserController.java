package com.eghm.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.constant.CacheConstant;
import com.eghm.constant.CommonConstant;
import com.eghm.dto.IdDTO;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.dto.ext.UserToken;
import com.eghm.dto.user.*;
import com.eghm.model.SysUser;
import com.eghm.service.cache.CacheService;
import com.eghm.service.sys.SysRoleService;
import com.eghm.service.sys.SysUserService;
import com.eghm.utils.DataUtil;
import com.eghm.vo.user.SysUserResponse;
import com.eghm.vo.user.UserResponse;
import com.google.common.base.Joiner;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/listPage")
    @ApiOperation("管理后台用户列表")
    public RespBody<PageData<SysUserResponse>> listPage(UserQueryRequest request) {
        Page<SysUserResponse> page = sysUserService.getByPage(request);
        return RespBody.success(PageData.toPage(page));
    }

    @PostMapping("/changePwd")
    @ApiOperation("修改登录人的密码")
    public RespBody<Void> changePwd(@Validated @RequestBody PasswordEditRequest request) {
        request.setUserId(SecurityHolder.getUserId());
        sysUserService.updateLoginPassword(request);
        return RespBody.success();
    }

    @PostMapping("/create")
    @ApiOperation("添加管理人员")
    public RespBody<Void> create(@Validated @RequestBody UserAddRequest request) {
        sysUserService.create(request);
        return RespBody.success();
    }

    @GetMapping("/select")
    @ApiOperation("查询系统用户信息")
    public RespBody<UserResponse> select(@Validated IdDTO dto) {
        SysUser user = sysUserService.getById(dto.getId());
        UserResponse response = DataUtil.copy(user, UserResponse.class);
        List<Long> roleList = sysRoleService.getByUserId(dto.getId());
        response.setRoleIds(Joiner.on(",").join(roleList));
        return RespBody.success(response);
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
        UserToken user = SecurityHolder.getUserRequired();
        cacheService.setValue(CacheConstant.LOCK_SCREEN + user.getId(), true, CommonConstant.MAX_LOCK_SCREEN);
        return RespBody.success();
    }

    @PostMapping("/unlockScreen")
    @ApiOperation("解锁操作")
    @ApiImplicitParam(name = "password", value = "密码", required = true)
    public RespBody<Void> unlockScreen(@RequestBody @Validated CheckPwdRequest request) {
        Long userId = SecurityHolder.getUserId();
        sysUserService.checkPassword(userId, request.getPwd());
        cacheService.delete(CacheConstant.LOCK_SCREEN + userId);
        return RespBody.success();
    }

    @PostMapping("/lock")
    @ApiOperation("锁定用户")
    public RespBody<Void> lock(@Validated @RequestBody IdDTO request) {
        sysUserService.lockUser(request.getId());
        return RespBody.success();
    }

    @PostMapping("/unlock")
    @ApiOperation("解锁用户")
    public RespBody<Void> unlock(@Validated @RequestBody IdDTO request) {
        sysUserService.unlockUser(request.getId());
        return RespBody.success();
    }

    @PostMapping("/delete")
    @ApiOperation("删除用户")
    public RespBody<Void> delete(@Validated @RequestBody IdDTO request) {
        sysUserService.deleteById(request.getId());
        return RespBody.success();
    }

    @PostMapping("/reset")
    @ApiOperation("重置密码")
    public RespBody<Void> reset(@Validated @RequestBody IdDTO request) {
        sysUserService.resetPassword(request.getId());
        return RespBody.success();
    }

}
