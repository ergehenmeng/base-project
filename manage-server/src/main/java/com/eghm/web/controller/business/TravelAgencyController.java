package com.eghm.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.common.enums.ref.PlatformState;
import com.eghm.common.enums.ref.State;
import com.eghm.model.TravelAgency;
import com.eghm.model.dto.IdDTO;
import com.eghm.model.dto.business.travel.TravelAgencyAddRequest;
import com.eghm.model.dto.business.travel.TravelAgencyEditRequest;
import com.eghm.model.dto.business.travel.TravelAgencyQueryRequest;
import com.eghm.model.dto.ext.PageData;
import com.eghm.model.dto.ext.RespBody;
import com.eghm.service.business.CommonService;
import com.eghm.service.business.TravelAgencyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 殿小二
 * @date 2023/2/22
 */
@RestController
@Api(tags = "旅行社")
@AllArgsConstructor
@RequestMapping("/manage/travel")
public class TravelAgencyController {
    
    private final TravelAgencyService travelAgencyService;
    
    private final CommonService commonService;
    
    @GetMapping("/listPage")
    @ApiOperation("旅行社列表")
    public PageData<TravelAgency> listPage(TravelAgencyQueryRequest request) {
        Page<TravelAgency> roomPage = travelAgencyService.getByPage(request);
        return PageData.toPage(roomPage);
    }
    
    @PostMapping("/create")
    @ApiOperation("新增商家")
    public RespBody<Void> create(@Validated @RequestBody TravelAgencyAddRequest request) {
        travelAgencyService.create(request);
        return RespBody.success();
    }
    
    /**
     * 对于注册的商户来说, 首次编辑商家信息即为开通商户
     */
    @PostMapping("/update")
    @ApiOperation("更新商家")
    public RespBody<Void> update(@Validated @RequestBody TravelAgencyEditRequest request) {
        travelAgencyService.update(request);
        return RespBody.success();
    }
    
    @PostMapping("/shelves")
    @ApiOperation("上架")
    public RespBody<Void> shelves(@Validated @RequestBody IdDTO dto) {
        travelAgencyService.updateState(dto.getId(), State.SHELVE);
        return RespBody.success();
    }
    
    @PostMapping("/unShelves")
    @ApiOperation("下架")
    public RespBody<Void> unShelves(@Validated @RequestBody IdDTO dto) {
        travelAgencyService.updateState(dto.getId(), State.UN_SHELVE);
        return RespBody.success();
    }
    
    @PostMapping("/platformAudit")
    @ApiOperation("平台上架审核")
    public RespBody<Void> updateState(@RequestBody @Validated IdDTO dto) {
        travelAgencyService.updateAuditState(dto.getId(), PlatformState.SHELVE);
        return RespBody.success();
    }
    
    @PostMapping("/platformUnShelves")
    @ApiOperation("平台下架")
    public RespBody<Void> platformUnShelves(@RequestBody @Validated IdDTO dto) {
        travelAgencyService.updateAuditState(dto.getId(), PlatformState.UN_SHELVE);
        return RespBody.success();
    }
    
    @GetMapping("/select")
    @ApiOperation("详情")
    public RespBody<TravelAgency> select(@Validated IdDTO dto) {
        TravelAgency travelAgency = travelAgencyService.selectByIdRequired(dto.getId());
        commonService.checkIllegal(travelAgency.getMerchantId());
        return RespBody.success(travelAgency);
    }
    
    @PostMapping("/delete")
    @ApiOperation("删除")
    public RespBody<Void> delete(@RequestBody @Validated IdDTO dto) {
        travelAgencyService.deleteById(dto.getId());
        return RespBody.success();
    }
}
