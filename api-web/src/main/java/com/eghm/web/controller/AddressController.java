package com.eghm.web.controller;

import com.eghm.model.dto.IdDTO;
import com.eghm.model.dto.address.AddressAddDTO;
import com.eghm.model.dto.address.AddressEditDTO;
import com.eghm.model.dto.ext.ApiHolder;
import com.eghm.model.dto.ext.RespBody;
import com.eghm.model.vo.user.AddressVO;
import com.eghm.service.user.UserAddressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @author 殿小二
 */
@RestController
@Api(tags = "用户地址")
@AllArgsConstructor
public class AddressController {

    private final UserAddressService userAddressService;

    /**
     * 添加用户收货地址
     */
    @PostMapping("/address/save")
    @ApiOperation("添加地址")
    public RespBody<Object> save(@RequestBody @Valid AddressAddDTO request) {
        request.setUserId(ApiHolder.getUserId());
        userAddressService.addUserAddress(request);
        return RespBody.success();
    }

    /**
     * 设置默认收货地址
     */
    @PostMapping("/address/set_default")
    @ApiOperation("设置默认地址")
    public RespBody<Object> setDefault(@RequestBody @Valid IdDTO request) {
        userAddressService.setDefault(request.getId(), ApiHolder.getUserId());
        return RespBody.success();
    }

    /**
     * 用户地址列表
     */
    @GetMapping("/address/list")
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
    public RespBody<Object> delete(@RequestBody @Valid IdDTO request) {
        userAddressService.deleteAddress(request.getId(), ApiHolder.getUserId());
        return RespBody.success();
    }

    /**
     * 编辑地址
     */
    @PostMapping("/address/update")
    @ApiOperation("编辑地址")
    public RespBody<Object> update(@RequestBody @Valid AddressEditDTO request) {
        request.setUserId(ApiHolder.getUserId());
        userAddressService.updateAddress(request);
        return RespBody.success();
    }

}
