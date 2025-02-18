package com.eghm.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.IdDTO;
import com.eghm.dto.business.redeem.RedeemCodeAddRequest;
import com.eghm.dto.business.redeem.RedeemCodeEditRequest;
import com.eghm.dto.business.redeem.RedeemCodeGrantQueryRequest;
import com.eghm.dto.business.redeem.RedeemCodeQueryRequest;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.model.RedeemCode;
import com.eghm.service.business.RedeemCodeGrantService;
import com.eghm.service.business.RedeemCodeService;
import com.eghm.utils.EasyExcelUtil;
import com.eghm.vo.business.redeem.RedeemCodeGrantResponse;
import com.eghm.vo.business.redeem.RedeemDetailResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2024/2/18
 */

@RestController
@Tag(name="兑换码")
@AllArgsConstructor
@RequestMapping(value = "/manage/redeem/code", produces = MediaType.APPLICATION_JSON_VALUE)
public class RedeemCodeController {

    private final RedeemCodeService redeemCodeService;

    private final RedeemCodeGrantService redeemCodeGrantService;

    @GetMapping("/listPage")
    @Operation(summary = "列表")
    public RespBody<PageData<RedeemCode>> listPage(RedeemCodeQueryRequest request) {
        Page<RedeemCode> listPage = redeemCodeService.listPage(request);
        return RespBody.success(PageData.toPage(listPage));
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "创建")
    public RespBody<Void> create(@Validated @RequestBody RedeemCodeAddRequest request) {
        redeemCodeService.create(request);
        return RespBody.success();
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "更新")
    public RespBody<Void> update(@Validated @RequestBody RedeemCodeEditRequest request) {
        redeemCodeService.update(request);
        return RespBody.success();
    }

    @GetMapping("/detail")
    @Operation(summary = "详情")
    public RespBody<RedeemDetailResponse> detail(@Validated IdDTO dto) {
        RedeemDetailResponse response =  redeemCodeService.detail(dto.getId());
        return RespBody.success(response);
    }

    @PostMapping(value = "/generate", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "生成兑换码")
    public RespBody<Void> generate(@RequestBody @Validated IdDTO dto) {
        redeemCodeService.generate(dto.getId());
        return RespBody.success();
    }

    @PostMapping(value = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "删除")
    public RespBody<Void> delete(@RequestBody @Validated IdDTO dto) {
        redeemCodeService.delete(dto.getId());
        return RespBody.success();
    }

    @GetMapping("/grant/listPage")
    @Operation(summary = "兑换码列表")
    public RespBody<PageData<RedeemCodeGrantResponse>> grantListPage(@Validated RedeemCodeGrantQueryRequest request) {
        Page<RedeemCodeGrantResponse> listPage = redeemCodeGrantService.listPage(request);
        return RespBody.success(PageData.toPage(listPage));
    }

    @GetMapping("/grant/export")
    @Operation(summary = "导出")
    public void grantExport(HttpServletResponse response, @Validated RedeemCodeGrantQueryRequest request) {
        List<RedeemCodeGrantResponse> byPage = redeemCodeGrantService.getList(request);
        EasyExcelUtil.export(response, "兑换码", byPage, RedeemCodeGrantResponse.class);
    }
}
