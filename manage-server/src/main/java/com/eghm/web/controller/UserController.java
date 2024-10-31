package com.eghm.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.annotation.SkipPerm;
import com.eghm.cache.CacheService;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.constants.CacheConstant;
import com.eghm.constants.CommonConstant;
import com.eghm.dto.IdDTO;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.dto.ext.UserToken;
import com.eghm.dto.user.*;
import com.eghm.enums.UserState;
import com.eghm.model.SysUser;
import com.eghm.service.sys.SysRoleService;
import com.eghm.service.sys.SysUserService;
import com.eghm.utils.DataUtil;
import com.eghm.vo.user.UserDetailResponse;
import com.eghm.vo.user.UserResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2018/11/26 17:10
 */
@RestController
@Api(tags = "系统用户管理")
@AllArgsConstructor
@RequestMapping(value = "/manage/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private final CacheService cacheService;

    private final SysUserService sysUserService;

    private final SysRoleService sysRoleService;

    @GetMapping("/listPage")
    @ApiOperation("列表")
    public RespBody<PageData<UserResponse>> listPage(UserQueryRequest request) {
        Page<UserResponse> page = sysUserService.getByPage(request);
        return RespBody.success(PageData.toPage(page));
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("新增")
    public RespBody<Void> create(@Validated @RequestBody UserAddRequest request) {
        sysUserService.create(request);
        return RespBody.success();
    }

    @GetMapping("/select")
    @ApiOperation("详情")
    public RespBody<UserDetailResponse> select(@Validated IdDTO dto) {
        SysUser user = sysUserService.getByIdRequired(dto.getId());
        UserDetailResponse response = DataUtil.copy(user, UserDetailResponse.class);
        List<Long> roleList = sysRoleService.getByUserId(dto.getId());
        response.setRoleIds(roleList);
        return RespBody.success(response);
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("编辑")
    public RespBody<Void> update(@Validated @RequestBody UserEditRequest request) {
        sysUserService.update(request);
        return RespBody.success();
    }

    @PostMapping("/lockScreen")
    @ApiOperation("锁屏")
    @SkipPerm
    public RespBody<Void> lockScreen() {
        UserToken user = SecurityHolder.getUserRequired();
        cacheService.setValue(CacheConstant.LOCK_SCREEN + user.getId(), true, CommonConstant.MAX_LOCK_SCREEN);
        return RespBody.success();
    }

    @PostMapping(value = "/unlockScreen", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("解锁屏幕")
    @SkipPerm
    public RespBody<Void> unlockScreen(@RequestBody @Validated CheckPwdRequest request) {
        Long userId = SecurityHolder.getUserId();
        sysUserService.checkPassword(userId, request.getPwd());
        cacheService.delete(CacheConstant.LOCK_SCREEN + userId);
        return RespBody.success();
    }

    @PostMapping(value = "/lock", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("锁定")
    public RespBody<Void> lock(@Validated @RequestBody IdDTO request) {
        sysUserService.updateState(request.getId(), UserState.LOCK);
        return RespBody.success();
    }

    @PostMapping(value = "/unlock", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("解锁")
    public RespBody<Void> unlock(@Validated @RequestBody IdDTO request) {
        sysUserService.updateState(request.getId(), UserState.NORMAL);
        return RespBody.success();
    }

    @PostMapping(value = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("删除")
    public RespBody<Void> delete(@Validated @RequestBody IdDTO request) {
        sysUserService.deleteById(request.getId());
        return RespBody.success();
    }

    @PostMapping(value = "/reset", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("重置密码")
    public RespBody<Void> reset(@Validated @RequestBody IdDTO request) {
        sysUserService.resetPassword(request.getId());
        return RespBody.success();
    }

    @PostMapping(value = "/changePwd", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("修改密码")
    @SkipPerm
    public RespBody<Void> changePwd(@Validated @RequestBody PasswordEditRequest request) {
        request.setUserId(SecurityHolder.getUserId());
        sysUserService.updateLoginPassword(request);
        return RespBody.success();
    }

}
