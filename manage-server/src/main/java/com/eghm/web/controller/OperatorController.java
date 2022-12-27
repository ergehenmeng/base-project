package com.eghm.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.common.constant.CacheConstant;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.model.SysMenu;
import com.eghm.model.SysOperator;
import com.eghm.model.dto.IdDTO;
import com.eghm.model.dto.ext.JwtManage;
import com.eghm.model.dto.ext.PageData;
import com.eghm.model.dto.ext.RespBody;
import com.eghm.model.dto.operator.*;
import com.eghm.model.vo.operator.OperatorResponse;
import com.eghm.service.cache.CacheService;
import com.eghm.service.sys.SysMenuService;
import com.eghm.service.sys.SysOperatorService;
import com.eghm.service.sys.SysRoleService;
import com.eghm.utils.DataUtil;
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
@RequestMapping("/manage/operator")
public class OperatorController {

    private final SysOperatorService sysOperatorService;

    private final SysRoleService sysRoleService;

    private final CacheService cacheService;

    private final SysMenuService sysMenuService;

    @PostMapping("/changePwd")
    @ApiOperation("修改管理人员密码")
    public RespBody<Void> changePwd(@Validated @RequestBody PasswordEditRequest request) {
        request.setOperatorId(SecurityHolder.getManageId());
        sysOperatorService.updateLoginPassword(request);
        return RespBody.success();
    }

    @GetMapping("/listPage")
    @ApiOperation("管理后台用户列表")
    public PageData<SysOperator> listPage(OperatorQueryRequest request) {
        Page<SysOperator> page = sysOperatorService.getByPage(request);
        return PageData.toPage(page);
    }

    @PostMapping("/create")
    @ApiOperation("添加管理人员")
    public RespBody<Void> create(@Validated @RequestBody OperatorAddRequest request) {
        sysOperatorService.create(request);
        return RespBody.success();
    }

    @GetMapping("/select")
    @ApiOperation("查询系统用户信息")
    @ApiImplicitParam(name = "id", value = "id主键", required = true)
    public OperatorResponse select(@RequestParam("id") Long id) {
        SysOperator operator = sysOperatorService.getById(id);
        OperatorResponse response = DataUtil.copy(operator, OperatorResponse.class);
        List<Long> roleList = sysRoleService.getByOperatorId(id);
        response.setRoleIds(Joiner.on(",").join(roleList));
        return response;
    }

    @PostMapping("/update")
    @ApiOperation("系统用户信息")
    public RespBody<Void> update(@Validated @RequestBody OperatorEditRequest request) {
        sysOperatorService.update(request);
        return RespBody.success();
    }

    @PostMapping("/lockScreen")
    @ApiOperation("锁屏操作")
    public RespBody<Void> lockScreen() {
        JwtManage operator = SecurityHolder.getManageRequired();
        cacheService.setValue(CacheConstant.LOCK_SCREEN + operator.getId(), true);
        return RespBody.success();
    }

    @PostMapping("/unlockScreen")
    @ApiOperation("解锁操作")
    @ApiImplicitParam(name = "password", value = "密码", required = true)
    public RespBody<Void> unlockScreen(@RequestBody @Validated CheckPwdRequest request) {
        JwtManage operator = SecurityHolder.getManageRequired();
        // TODO 临时密码
        sysOperatorService.checkPassword(request.getPwd(), "");
        cacheService.delete(CacheConstant.LOCK_SCREEN + operator.getId());
        return RespBody.success();
    }

    @PostMapping("/lock")
    @ApiOperation("锁定用户")
    public RespBody<Void> lock(@Validated @RequestBody IdDTO request) {
        sysOperatorService.lockOperator(request.getId());
        return RespBody.success();
    }

    @PostMapping("/unlock")
    @ApiOperation("解锁用户")
    public RespBody<Void> unlock(@Validated @RequestBody IdDTO request) {
        sysOperatorService.unlockOperator(request.getId());
        return RespBody.success();
    }

    @PostMapping("/delete")
    @ApiOperation("删除用户")
    public RespBody<Void> delete(@Validated @RequestBody IdDTO request) {
        sysOperatorService.delete(request.getId());
        return RespBody.success();
    }

    @PostMapping("/reset")
    @ApiOperation("重置密码")
    public RespBody<Void> reset(@Validated @RequestBody IdDTO request) {
        sysOperatorService.resetPassword(request.getId());
        return RespBody.success();
    }

    @GetMapping("/menuList")
    @ApiOperation("查询自己拥有的菜单列表")
    public List<SysMenu> menuList() {
        return sysMenuService.getList(SecurityHolder.getManageId());
    }
}
