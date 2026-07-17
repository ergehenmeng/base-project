package com.eghm.app.manage.controller.sys;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.foundation.core.annotation.SkipPerm;
import com.eghm.foundation.core.enums.UserType;
import com.eghm.integration.storage.service.FileService;
import com.eghm.foundation.core.configuration.authentication.SecurityHolder;
import com.eghm.foundation.core.constants.CommonConstant;
import com.eghm.foundation.core.dto.IdDTO;
import com.eghm.integration.storage.dto.FilePath;
import com.eghm.foundation.core.dto.ext.PageData;
import com.eghm.foundation.core.dto.ext.RespBody;
import com.eghm.platform.iam.dto.PasswordEditRequest;
import com.eghm.platform.iam.dto.UserAddRequest;
import com.eghm.platform.iam.dto.UserEditRequest;
import com.eghm.platform.iam.dto.UserProfileRequest;
import com.eghm.platform.iam.dto.UserQueryRequest;
import com.eghm.foundation.core.enums.UserState;
import com.eghm.platform.iam.entity.SysUser;
import com.eghm.platform.iam.service.SysRoleService;
import com.eghm.platform.iam.service.SysUserService;
import com.eghm.foundation.web.utility.DataUtil;
import com.eghm.foundation.web.utility.FileUtil;
import com.eghm.platform.iam.vo.UserDetailResponse;
import com.eghm.platform.iam.vo.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2018/11/26 17:10
 */
@RestController
@AllArgsConstructor
@Tag(name = "系统用户管理")
@RequestMapping(value = "/manage/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private final FileService fileService;

    private final SysUserService sysUserService;

    private final SysRoleService sysRoleService;

    @GetMapping("/listPage")
    @Operation(summary = "列表")
    public RespBody<PageData<UserResponse>> listPage(@ParameterObject UserQueryRequest request) {
        request.setUserType(UserType.SYS_USER.getValue());
        Page<UserResponse> page = sysUserService.getByPage(request);
        return RespBody.success(PageData.convert(page));
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "新增")
    public RespBody<Void> create(@Validated @RequestBody UserAddRequest request) {
        sysUserService.create(request);
        return RespBody.success();
    }

    @GetMapping("/select")
    @Operation(summary = "详情")
    public RespBody<UserDetailResponse> select(@ParameterObject @Validated IdDTO dto) {
        SysUser user = sysUserService.getByIdRequired(dto.getId());
        UserDetailResponse response = DataUtil.copy(user, UserDetailResponse.class);
        List<Long> roleList = sysRoleService.getByUserId(dto.getId());
        response.setRoleIds(roleList);
        return RespBody.success(response);
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "编辑")
    public RespBody<Void> update(@Validated @RequestBody UserEditRequest request) {
        sysUserService.update(request);
        return RespBody.success();
    }

    @PostMapping(value = "/lock", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "锁定")
    public RespBody<Void> lock(@Validated @RequestBody IdDTO request) {
        sysUserService.updateState(request.getId(), UserState.LOCK);
        return RespBody.success();
    }

    @PostMapping(value = "/unlock", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "解锁")
    public RespBody<Void> unlock(@Validated @RequestBody IdDTO request) {
        sysUserService.updateState(request.getId(), UserState.NORMAL);
        return RespBody.success();
    }

    @PostMapping(value = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "删除")
    public RespBody<Void> delete(@Validated @RequestBody IdDTO request) {
        sysUserService.deleteById(request.getId());
        return RespBody.success();
    }

    @PostMapping(value = "/reset", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "重置密码")
    public RespBody<Void> reset(@Validated @RequestBody IdDTO request) {
        sysUserService.resetPassword(request.getId());
        return RespBody.success();
    }

    @PostMapping(value = "/changePwd", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "修改密码")
    @SkipPerm
    public RespBody<Void> changePwd(@Validated @RequestBody PasswordEditRequest request) {
        request.setUserId(SecurityHolder.getUserId());
        sysUserService.updateLoginPassword(request);
        return RespBody.success();
    }

    @PostMapping(value = "/unbindTotp", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "取消双因子绑定")
    public RespBody<Void> unbindTotp(@Validated @RequestBody IdDTO request) {
        sysUserService.unBindTotp(request.getId());
        return RespBody.success();
    }

    @PostMapping(value = "/updateAvatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Parameter(name = "file", description = "file流", required = true, schema = @Schema(type = "string", format = "binary"))
    @Operation(summary = "头像上传")
    @SkipPerm
    public RespBody<FilePath> updateAvatar(@RequestParam("file") MultipartFile file) {
        FileUtil.checkFileType(file, "png", "jpg", "jpeg");
        FilePath filePath = fileService.saveFile(CommonConstant.MANAGE + SecurityHolder.getUserId(), file, CommonConstant.AVATAR_FOLDER);
        sysUserService.updateAvatar(SecurityHolder.getUserId(), filePath.host() + filePath.path());
        return RespBody.success(filePath);
    }

    @PostMapping(value = "/updateBasic", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "更新基础信息")
    @SkipPerm
    public RespBody<Void> updateBasic(@Validated @RequestBody UserProfileRequest request) {
        sysUserService.updateProfile(request);
        return RespBody.success();
    }
}
