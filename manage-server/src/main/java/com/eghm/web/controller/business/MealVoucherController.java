package com.eghm.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.dto.IdDTO;
import com.eghm.dto.business.restaurant.voucher.MealVoucherAddRequest;
import com.eghm.dto.business.restaurant.voucher.MealVoucherEditRequest;
import com.eghm.dto.business.restaurant.voucher.VoucherQueryRequest;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.enums.ref.State;
import com.eghm.model.MealVoucher;
import com.eghm.service.business.MealVoucherService;
import com.eghm.utils.ExcelUtil;
import com.eghm.vo.business.restaurant.VoucherResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author 二哥很猛
 * @date 2022/6/30
 */
@RestController
@Api(tags = "餐饮券")
@AllArgsConstructor
@RequestMapping("/manage/restaurant/voucher")
public class MealVoucherController {

    private final MealVoucherService mealVoucherService;

    @GetMapping("/listPage")
    @ApiOperation("餐饮券列表")
    public RespBody<PageData<MealVoucher>> listPage(VoucherQueryRequest request) {
        Page<MealVoucher> roomPage = mealVoucherService.getByPage(request);
        return RespBody.success(PageData.toPage(roomPage));
    }

    @PostMapping("/create")
    @ApiOperation("新增餐饮券")
    public RespBody<Void> create(@Validated @RequestBody MealVoucherAddRequest request) {
        mealVoucherService.create(request);
        return RespBody.success();
    }

    @PostMapping("/update")
    @ApiOperation("更新餐饮券")
    public RespBody<Void> update(@Validated @RequestBody MealVoucherEditRequest request) {
        mealVoucherService.update(request);
        return RespBody.success();
    }

    @PostMapping("/shelves")
    @ApiOperation("上架")
    public RespBody<Void> shelves(@Validated @RequestBody IdDTO dto) {
        mealVoucherService.updateState(dto.getId(), State.SHELVE);
        return RespBody.success();
    }

    @PostMapping("/unShelves")
    @ApiOperation("下架")
    public RespBody<Void> unShelves(@Validated @RequestBody IdDTO dto) {
        mealVoucherService.updateState(dto.getId(), State.UN_SHELVE);
        return RespBody.success();
    }

    @PostMapping("/platformUnShelves")
    @ApiOperation("平台下架")
    public RespBody<Void> platformUnShelves(@RequestBody @Validated IdDTO dto) {
        mealVoucherService.updateState(dto.getId(), State.FORCE_UN_SHELVE);
        return RespBody.success();
    }

    @GetMapping("/select")
    @ApiOperation("餐饮券详情")
    public RespBody<MealVoucher> select(@Validated IdDTO dto) {
        MealVoucher voucher = mealVoucherService.selectByIdRequired(dto.getId());
        return RespBody.success(voucher);
    }

    @PostMapping("/delete")
    @ApiOperation("删除商品")
    public RespBody<Void> delete(@RequestBody @Validated IdDTO dto) {
        mealVoucherService.deleteById(dto.getId());
        return RespBody.success();
    }

    @GetMapping("/export")
    @ApiOperation("导出")
    public void export(HttpServletResponse response, VoucherQueryRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        List<VoucherResponse> byPage = mealVoucherService.getList(request);
        ExcelUtil.export(response, "餐饮券", byPage, VoucherResponse.class);
    }

}
