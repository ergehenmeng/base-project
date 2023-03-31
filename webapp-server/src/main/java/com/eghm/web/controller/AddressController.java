package com.eghm.web.controller;

import com.eghm.dto.IdDTO;
import com.eghm.dto.address.AddressAddDTO;
import com.eghm.dto.address.AddressEditDTO;
import com.eghm.dto.ext.ApiHolder;
import com.eghm.dto.ext.RespBody;
import com.eghm.vo.user.AddressVO;
import com.eghm.service.user.UserAddressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 殿小二
 */
@RestController
@Api(tags = "用户地址")
@AllArgsConstructor
@RequestMapping("/webapp/user/address")
public class AddressController {

    private final UserAddressService userAddressService;

    @PostMapping("/save")
    @ApiOperation("添加地址")
    public RespBody<Void> save(@RequestBody @Validated AddressAddDTO request) {
        request.setUserId(ApiHolder.getUserId());
        userAddressService.addUserAddress(request);
        return RespBody.success();
    }

    @PostMapping("/default")
    @ApiOperation("设置默认地址")
    public RespBody<Void> setDefault(@RequestBody @Validated IdDTO request) {
        userAddressService.setDefault(request.getId(), ApiHolder.getUserId());
        return RespBody.success();
    }

    @GetMapping("/list")
    @ApiOperation("用户地址列表")
    public RespBody<List<AddressVO>> list() {
        List<AddressVO> voList = userAddressService.getByUserId(ApiHolder.getUserId());
        return RespBody.success(voList);
    }

    @PostMapping("/delete")
    @ApiOperation("设置默认地址")
    public RespBody<Void> delete(@RequestBody @Validated IdDTO request) {
        userAddressService.deleteAddress(request.getId(), ApiHolder.getUserId());
        return RespBody.success();
    }

    @PostMapping("/update")
    @ApiOperation("编辑地址")
    public RespBody<Void> update(@RequestBody @Validated AddressEditDTO request) {
        request.setUserId(ApiHolder.getUserId());
        userAddressService.updateAddress(request);
        return RespBody.success();
    }

}
