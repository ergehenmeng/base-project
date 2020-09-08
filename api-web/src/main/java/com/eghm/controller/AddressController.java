package com.eghm.controller;

import com.eghm.annotation.SkipLogger;
import com.eghm.model.dto.IdDTO;
import com.eghm.model.dto.address.AddressAddDTO;
import com.eghm.model.ext.ApiHolder;
import com.eghm.model.ext.RespBody;
import com.eghm.model.vo.user.AddressVO;
import com.eghm.service.user.UserAddressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 殿小二
 * @date 2020/9/8
 */
@RestController
@Api(tags = "用户地址")
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
    @ApiOperation("添加地址")
    public RespBody<Object> save(AddressAddDTO request) {
        request.setUserId(ApiHolder.getUserId());
        userAddressService.addUserAddress(request);
        return RespBody.success();
    }

    /**
     * 设置默认收货地址
     */
    @PostMapping("/address/set_default")
    @ApiOperation("设置默认地址")
    public RespBody<Object> setDefault(IdDTO request) {
        userAddressService.setDefault(request.getId(), ApiHolder.getUserId());
        return RespBody.success();
    }

    /**
     * 用户地址列表
     */
    @PostMapping("/address/list")
    @ApiOperation("用户地址列表")
    public RespBody<List<AddressVO>> list() {
        List<AddressVO> voList = userAddressService.getByUserId(ApiHolder.getUserId());
        return RespBody.success(voList);
    }
}
