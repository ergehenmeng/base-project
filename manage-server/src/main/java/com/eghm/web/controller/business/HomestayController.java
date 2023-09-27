package com.eghm.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.dto.IdDTO;
import com.eghm.dto.business.homestay.HomestayAddRequest;
import com.eghm.dto.business.homestay.HomestayEditRequest;
import com.eghm.dto.business.homestay.HomestayQueryRequest;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.enums.ref.PlatformState;
import com.eghm.enums.ref.State;
import com.eghm.model.Homestay;
import com.eghm.service.business.HomestayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author 二哥很猛
 */
@RestController
@Api(tags = "民宿管理")
@AllArgsConstructor
@RequestMapping("/manage/homestay")
public class HomestayController {

    private final HomestayService homestayService;

    @GetMapping("/listPage")
    @ApiOperation("民宿列表")
    public RespBody<PageData<Homestay>> listPage(HomestayQueryRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        Page<Homestay> byPage = homestayService.getByPage(request);
        return RespBody.success(PageData.toPage(byPage));
    }

    @PostMapping("/create")
    @ApiOperation("创建民宿")
    public RespBody<Void> create(@RequestBody @Validated HomestayAddRequest request) {
        homestayService.create(request);
        return RespBody.success();
    }

    @PostMapping("/update")
    @ApiOperation("更新民宿")
    public RespBody<Void> update(@RequestBody @Validated HomestayEditRequest request) {
        homestayService.update(request);
        return RespBody.success();
    }

    @PostMapping("/shelves")
    @ApiOperation("上架")
    public RespBody<Void> shelves(@Validated @RequestBody IdDTO dto) {
        homestayService.updateState(dto.getId(), State.SHELVE);
        return RespBody.success();
    }

    @PostMapping("/unShelves")
    @ApiOperation("下架")
    public RespBody<Void> unShelves(@Validated @RequestBody IdDTO dto) {
        homestayService.updateState(dto.getId(), State.UN_SHELVE);
        return RespBody.success();
    }

    @PostMapping("/platformAudit")
    @ApiOperation("平台上架审核")
    public RespBody<Void> platformAudit(@RequestBody @Validated IdDTO dto) {
        homestayService.updateAuditState(dto.getId(), PlatformState.SHELVE);
        return RespBody.success();
    }

    @PostMapping("/platformUnShelves")
    @ApiOperation("平台下架")
    public RespBody<Void> platformUnShelves(@RequestBody @Validated IdDTO dto) {
        homestayService.updateAuditState(dto.getId(), PlatformState.UN_SHELVE);
        return RespBody.success();
    }

    @PostMapping("/delete")
    @ApiOperation("删除")
    public RespBody<Void> delete(@RequestBody @Validated IdDTO dto) {
        homestayService.deleteById(dto.getId());
        return RespBody.success();
    }
    
    
    @GetMapping("/select")
    @ApiOperation("详情")
    public RespBody<Homestay> select(@Validated IdDTO dto) {
        Homestay homestay = homestayService.selectByIdRequired(dto.getId());
        return RespBody.success(homestay);
    }

}
