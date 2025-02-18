package com.eghm.web.controller;

import com.eghm.dto.IdDTO;
import com.eghm.dto.ext.ApiHolder;
import com.eghm.dto.ext.RespBody;
import com.eghm.dto.member.address.AddressAddDTO;
import com.eghm.dto.member.address.AddressEditDTO;
import com.eghm.service.member.MemberAddressService;
import com.eghm.vo.member.AddressVO;
import com.eghm.web.annotation.AccessToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
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
@RequestMapping(value = "/webapp/member/address", produces = MediaType.APPLICATION_JSON_VALUE)
public class AddressController {

    private final MemberAddressService memberAddressService;

    @GetMapping("/list")
    @ApiOperation("列表")
    public RespBody<List<AddressVO>> list() {
        List<AddressVO> voList = memberAddressService.getByMemberId(ApiHolder.getMemberId());
        return RespBody.success(voList);
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("添加地址")
    public RespBody<Void> create(@RequestBody @Validated AddressAddDTO dto) {
        dto.setMemberId(ApiHolder.getMemberId());
        memberAddressService.addAddress(dto);
        return RespBody.success();
    }

    @PostMapping(value = "/default", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("设置默认地址")
    public RespBody<Void> setDefault(@RequestBody @Validated IdDTO dto) {
        memberAddressService.setDefault(dto.getId(), ApiHolder.getMemberId());
        return RespBody.success();
    }

    @PostMapping(value = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("删除地址")
    public RespBody<Void> delete(@RequestBody @Validated IdDTO dto) {
        memberAddressService.deleteAddress(dto.getId(), ApiHolder.getMemberId());
        return RespBody.success();
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("编辑地址")
    public RespBody<Void> update(@RequestBody @Validated AddressEditDTO dto) {
        dto.setMemberId(ApiHolder.getMemberId());
        memberAddressService.updateAddress(dto);
        return RespBody.success();
    }

}
