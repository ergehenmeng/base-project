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
import com.eghm.utils.ExcelUtil;
import com.eghm.vo.business.base.BaseStoreResponse;
import com.eghm.vo.business.redeem.RedeemCodeGrantResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2024/2/18
 */

@RestController
@Api(tags = "兑换码")
@AllArgsConstructor
@RequestMapping(value = "/manage/redeem/code", produces = MediaType.APPLICATION_JSON_VALUE)
public class RedeemCodeController {

    private final RedeemCodeService redeemCodeService;

    private final RedeemCodeGrantService redeemCodeGrantService;

    @GetMapping("/listPage")
    @ApiOperation("列表")
    public RespBody<PageData<RedeemCode>> listPage(RedeemCodeQueryRequest request) {
        Page<RedeemCode> listPage = redeemCodeService.listPage(request);
        return RespBody.success(PageData.toPage(listPage));
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("创建")
    public RespBody<Void> create(@Validated @RequestBody RedeemCodeAddRequest request) {
        redeemCodeService.create(request);
        return RespBody.success();
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("更新")
    public RespBody<Void> update(@Validated @RequestBody RedeemCodeEditRequest request) {
        redeemCodeService.update(request);
        return RespBody.success();
    }

    @GetMapping("/select")
    @ApiOperation("详情")
    public RespBody<RedeemCode> select(@Validated IdDTO dto) {
        RedeemCode select = redeemCodeService.selectByIdRequired(dto.getId());
        return RespBody.success(select);
    }

    @GetMapping("/scope")
    @ApiOperation("使用范围")
    public RespBody<List<BaseStoreResponse>> scope(@Validated IdDTO dto) {
        List<BaseStoreResponse> scopeList = redeemCodeService.getScopeList(dto.getId());
        return RespBody.success(scopeList);
    }

    @PostMapping(value = "/generate", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("生成兑换码")
    public RespBody<Void> generate(@RequestBody @Validated IdDTO dto) {
        redeemCodeService.generate(dto.getId());
        return RespBody.success();
    }

    @PostMapping(value = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("删除")
    public RespBody<Void> delete(@RequestBody @Validated IdDTO dto) {
        redeemCodeService.delete(dto.getId());
        return RespBody.success();
    }

    @GetMapping("/grant/listPage")
    @ApiOperation("列表")
    public RespBody<PageData<RedeemCodeGrantResponse>> grantListPage(@Validated RedeemCodeGrantQueryRequest request) {
        Page<RedeemCodeGrantResponse> listPage = redeemCodeGrantService.listPage(request);
        return RespBody.success(PageData.toPage(listPage));
    }

    @GetMapping("/grant/export")
    @ApiOperation("导出")
    public void grantExport(HttpServletResponse response, @Validated RedeemCodeGrantQueryRequest request) {
        List<RedeemCodeGrantResponse> byPage = redeemCodeGrantService.getList(request);
        ExcelUtil.export(response, "兑换码", byPage, RedeemCodeGrantResponse.class);
    }
}
