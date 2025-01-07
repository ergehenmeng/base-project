package com.eghm.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.annotation.SkipPerm;
import com.eghm.dto.IdDTO;
import com.eghm.dto.business.merchant.MerchantAddRequest;
import com.eghm.dto.business.merchant.MerchantEditRequest;
import com.eghm.dto.business.merchant.MerchantQueryRequest;
import com.eghm.dto.business.merchant.MerchantRateRequest;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.enums.MerchantType;
import com.eghm.model.Merchant;
import com.eghm.service.business.MerchantService;
import com.eghm.utils.DataUtil;
import com.eghm.utils.EasyExcelUtil;
import com.eghm.vo.business.merchant.BaseMerchantResponse;
import com.eghm.vo.business.merchant.MerchantDetailVO;
import com.eghm.vo.business.merchant.MerchantResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author 二哥很猛 2022/6/24 15:13
 */
@RestController
@Tag(name="商户管理")
@AllArgsConstructor
@RequestMapping(value = "/manage/merchant", produces = MediaType.APPLICATION_JSON_VALUE)
public class MerchantController {

    private final MerchantService merchantService;

    @GetMapping("/listPage")
    @Operation(summary = "列表")
    public RespBody<PageData<MerchantResponse>> listPage(@Validated MerchantQueryRequest request) {
        Page<MerchantResponse> merchantPage = merchantService.getByPage(request);
        return RespBody.success(PageData.toPage(merchantPage));
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "新增")
    public RespBody<Void> create(@RequestBody @Validated MerchantAddRequest request) {
        merchantService.create(request);
        return RespBody.success();
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "编辑")
    public RespBody<Void> update(@RequestBody @Validated MerchantEditRequest request) {
        merchantService.update(request);
        return RespBody.success();
    }

    @GetMapping("/detail")
    @Operation(summary = "详情")
    public RespBody<MerchantDetailVO> detail(@Validated IdDTO dto) {
        Merchant merchant = merchantService.selectByIdRequired(dto.getId());
        MerchantDetailVO vo = DataUtil.copy(merchant, MerchantDetailVO.class);
        vo.setTypeList(MerchantType.parse(merchant.getType()));
        return RespBody.success(vo);
    }

    @PostMapping(value = "/lock", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "账号锁定")
    public RespBody<Void> lock(@RequestBody @Validated IdDTO dto) {
        merchantService.lock(dto.getId());
        return RespBody.success();
    }

    @PostMapping(value = "/unlock", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "账号解锁")
    public RespBody<Void> unlock(@RequestBody @Validated IdDTO dto) {
        merchantService.unlock(dto.getId());
        return RespBody.success();
    }

    @PostMapping(value = "/unbind", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "解绑")
    public RespBody<Void> unbind(@RequestBody @Validated IdDTO dto) {
        merchantService.unbind(dto.getId());
        return RespBody.success();
    }

    @PostMapping(value = "/resetPwd", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "重置密码")
    public RespBody<Void> resetPwd(@RequestBody @Validated IdDTO dto) {
        merchantService.resetPwd(dto.getId());
        return RespBody.success();
    }

    @PostMapping(value = "/adjustRate", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "调整费率")
    public RespBody<Void> adjustRate(@RequestBody @Validated MerchantRateRequest request) {
        merchantService.adjustRate(request.getId(), request.getPlatformServiceRate());
        return RespBody.success();
    }

    @PostMapping(value = "/logout", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "注销")
    public RespBody<Void> logout(@RequestBody @Validated IdDTO dto) {
        merchantService.logout(dto.getId());
        return RespBody.success();
    }

    @GetMapping("/export")
    @Operation(summary = "导出")
    public void export(HttpServletResponse response, MerchantQueryRequest request) {
        List<MerchantResponse> byPage = merchantService.getList(request);
        EasyExcelUtil.export(response, "商户信息", byPage, MerchantResponse.class);
    }
    
    @GetMapping("/list")
    @Operation(summary = "列表")
    @SkipPerm
    public RespBody<List<BaseMerchantResponse>> list() {
        List<BaseMerchantResponse> merchantList = merchantService.getList();
        return RespBody.success(merchantList);
    }
}
