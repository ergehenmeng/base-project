package com.eghm.web.controller;

import com.eghm.model.dto.wechat.MpLoginDTO;
import com.eghm.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author wyb
 * @date 2021/12/18 14:47
 */
@RestController
@Api(tags = "微信授权")
@RequestMapping("/wechat")
public class WeChatController {

    @Autowired
    private UserService userService;

    @PostMapping("/mp/login")
    @ApiOperation("微信授权登陆(自动注册)")
    private void login(@Valid @RequestBody MpLoginDTO dto) {

    }

}
