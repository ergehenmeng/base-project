package com.fanyin.controller;

import com.fanyin.model.ext.RespBody;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 二哥很猛
 * @date 2019/9/3 17:07
 */
@RestController
@Api("用户相关接口")
public class UserController extends AbstractController {

    @PostMapping("/user/set_password")
    public RespBody setPassword(){

        return RespBody.getInstance();
    }
}