package com.eghm.web.controller;

import com.eghm.dto.ext.RespBody;
import com.eghm.dto.wechat.MpLoginDTO;
import com.eghm.service.user.UserService;
import com.eghm.utils.IpUtil;
import com.eghm.vo.login.LoginTokenVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 二哥很猛
 * @date 2021/12/18 14:47
 */
@RestController
@Api(tags = "微信授权")
@RequestMapping("/webapp/wechat")
@AllArgsConstructor
public class WeChatController {

    private final UserService userService;

    @PostMapping("/mp/login")
    @ApiOperation("微信授权登陆(自动注册)")
    public RespBody<LoginTokenVO> login(@Validated @RequestBody MpLoginDTO dto, HttpServletRequest request) {
        LoginTokenVO mpLogin = userService.mpLogin(dto.getCode(), IpUtil.getIpAddress(request));
        return RespBody.success(mpLogin);
    }

}
