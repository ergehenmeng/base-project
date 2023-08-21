package com.eghm.web.controller;

import com.eghm.dto.IdDTO;
import com.eghm.dto.address.AddressAddDTO;
import com.eghm.dto.address.AddressEditDTO;
import com.eghm.dto.ext.ApiHolder;
import com.eghm.dto.ext.RespBody;
import com.eghm.vo.member.AddressVO;
import com.eghm.service.member.MemberAddressService;
import com.eghm.web.annotation.AccessToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 殿小二
 */
@AccessToken
@RestController
@Api(tags = "用户地址")
@AllArgsConstructor
@RequestMapping("/webapp/member/address")
public class AddressController {

    private final MemberAddressService memberAddressService;

    @GetMapping("/list")
    @ApiOperation("用户地址列表")
    public RespBody<List<AddressVO>> list() {
        List<AddressVO> voList = memberAddressService.getByMemberId(ApiHolder.getMemberId());
        return RespBody.success(voList);
    }

    @PostMapping("/save")
    @ApiOperation("添加地址")
    public RespBody<Void> save(@RequestBody @Validated AddressAddDTO request) {
        request.setMemberId(ApiHolder.getMemberId());
        memberAddressService.addAddress(request);
        return RespBody.success();
    }

    @PostMapping("/default")
    @ApiOperation("设置默认地址")
    public RespBody<Void> setDefault(@RequestBody @Validated IdDTO request) {
        memberAddressService.setDefault(request.getId(), ApiHolder.getMemberId());
        return RespBody.success();
    }

    @PostMapping("/delete")
    @ApiOperation("设置默认地址")
    public RespBody<Void> delete(@RequestBody @Validated IdDTO request) {
        memberAddressService.deleteAddress(request.getId(), ApiHolder.getMemberId());
        return RespBody.success();
    }

    @PostMapping("/update")
    @ApiOperation("编辑地址")
    public RespBody<Void> update(@RequestBody @Validated AddressEditDTO request) {
        request.setMemberId(ApiHolder.getMemberId());
        memberAddressService.updateAddress(request);
        return RespBody.success();
    }

}
