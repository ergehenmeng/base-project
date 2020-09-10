package com.eghm.controller;

import com.eghm.annotation.SkipLogger;
import com.eghm.model.dto.IdDTO;
import com.eghm.model.dto.address.AddressAddDTO;
import com.eghm.model.dto.address.AddressEditDTO;
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

    /**
     * 设置默认收货地址
     */
    @PostMapping("/address/delete")
    @ApiOperation("设置默认地址")
    public RespBody<Object> delete(IdDTO request) {
        userAddressService.deleteAddress(request.getId(), ApiHolder.getUserId());
        return RespBody.success();
    }

    /**
     * 编辑地址
     */
    @PostMapping("/address/update")
    @ApiOperation("编辑地址")
    public RespBody<Object> update(AddressEditDTO request) {
        request.setUserId(ApiHolder.getUserId());
        userAddressService.updateAddress(request);
        return RespBody.success();
    }

}
