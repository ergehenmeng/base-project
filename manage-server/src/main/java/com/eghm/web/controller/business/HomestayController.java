package com.eghm.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.dto.IdDTO;
import com.eghm.dto.business.base.BaseStoreQueryRequest;
import com.eghm.dto.business.homestay.HomestayAddRequest;
import com.eghm.dto.business.homestay.HomestayEditRequest;
import com.eghm.dto.business.homestay.HomestayQueryRequest;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.enums.ref.State;
import com.eghm.model.Homestay;
import com.eghm.service.business.HomestayService;
import com.eghm.utils.ExcelUtil;
import com.eghm.vo.business.base.BaseStoreResponse;
import com.eghm.vo.business.homestay.HomestayResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

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
    public RespBody<PageData<HomestayResponse>> listPage(HomestayQueryRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        Page<HomestayResponse> byPage = homestayService.getByPage(request);
        return RespBody.success(PageData.toPage(byPage));
    }

    @GetMapping("/storeListPage")
    @ApiOperation("列表含商户信息")
    public RespBody<PageData<BaseStoreResponse>> storeListPage(BaseStoreQueryRequest request) {
        Page<BaseStoreResponse> listPage = homestayService.getStorePage(request);
        return RespBody.success(PageData.toPage(listPage));
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

    @PostMapping("/platformUnShelves")
    @ApiOperation("平台下架")
    public RespBody<Void> platformUnShelves(@RequestBody @Validated IdDTO dto) {
        homestayService.updateState(dto.getId(), State.FORCE_UN_SHELVE);
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

    @GetMapping("/export")
    @ApiOperation("民宿导出")
    public void export(HttpServletResponse response, HomestayQueryRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        List<HomestayResponse> byPage = homestayService.getList(request);
        ExcelUtil.export(response, "民宿信息", byPage, HomestayResponse.class);
    }

}
