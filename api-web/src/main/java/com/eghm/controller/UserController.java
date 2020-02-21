package com.eghm.controller;

import com.eghm.model.ext.RespBody;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户相关信息
 *
 * @author 二哥很猛
 * @date 2019/9/3 17:07
 */
@RestController
@Api("用户相关接口")
public class UserController {

    @PostMapping("/user/set_password")
    public RespBody<Object> setPassword(){

        return RespBody.getInstance();
    }
}
