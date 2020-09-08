package com.eghm.controller;

import com.eghm.annotation.SkipLogger;
import com.eghm.model.dto.address.AddressAddDTO;
import com.eghm.model.ext.ApiHolder;
import com.eghm.model.ext.RespBody;
import com.eghm.service.user.UserAddressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 殿小二
 * @date 2020/9/8
 */
@RestController
@Api("用户地址")
public class AddressController {

    private UserAddressService userAddressService;

    @Autowired
    @SkipLogger
    public void setUserAddressService(UserAddressService userAddressService) {
        this.userAddressService = userAddressService;
    }

    /**
     * 添加用户收货地址
     */
    @PostMapping("/address/save")
    @ApiOperation("添加收货地址")
    public RespBody<Object> save(AddressAddDTO request) {
        request.setUserId(ApiHolder.getUserId());
        userAddressService.addUserAddress(request);
        return RespBody.success();
    }
}
