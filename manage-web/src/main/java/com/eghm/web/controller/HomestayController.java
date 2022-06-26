package com.eghm.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dao.model.Homestay;
import com.eghm.model.dto.IdStateDTO;
import com.eghm.model.dto.ext.PageData;
import com.eghm.model.dto.ext.RespBody;
import com.eghm.model.dto.homestay.HomestayAddRequest;
import com.eghm.model.dto.homestay.HomestayEditRequest;
import com.eghm.model.dto.homestay.HomestayQueryRequest;
import com.eghm.service.business.HomestayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 二哥很猛
 */
@RestController
@Api(tags = "民宿管理")
@AllArgsConstructor
@RequestMapping("/homestay")
public class HomestayController {

    private final HomestayService homestayService;

    @GetMapping("/listPage")
    @ApiOperation("民宿列表")
    public PageData<Homestay> listPage(HomestayQueryRequest request) {
        Page<Homestay> byPage = homestayService.getByPage(request);
        return PageData.toPage(byPage);
    }

    @GetMapping("/create")
    @ApiOperation("创建民宿")
    public RespBody<Void> create(@RequestBody @Validated HomestayAddRequest request) {
        homestayService.create(request);
        return RespBody.success();
    }

    @GetMapping("/update")
    @ApiOperation("更新民宿")
    public RespBody<Void> update(@RequestBody @Validated HomestayEditRequest request) {
        homestayService.update(request);
        return RespBody.success();
    }

    @GetMapping("/updateState")
    @ApiOperation("更新上下架状态")
    public RespBody<Void> updateState(@RequestBody @Validated IdStateDTO dto) {
        homestayService.updateState(dto.getId(), dto.getState());
        return RespBody.success();
    }

}
