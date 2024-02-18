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
import com.eghm.vo.business.redeem.RedeemCodeGrantResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2024/2/18
 */

@RestController
@Api(tags = "兑换码")
@AllArgsConstructor
@RequestMapping("/manage/redeem/code")
public class RedeemCodeController {

    private final RedeemCodeService redeemCodeService;

    private final RedeemCodeGrantService redeemCodeGrantService;

    @RequestMapping("/listPage")
    @ApiOperation("列表")
    public RespBody<PageData<RedeemCode>> listPage(RedeemCodeQueryRequest request) {
        Page<RedeemCode> listPage = redeemCodeService.listPage(request);
        return RespBody.success(PageData.toPage(listPage));
    }

    @RequestMapping("/create")
    @ApiOperation("创建")
    public RespBody<Void> create(@Validated @RequestBody RedeemCodeAddRequest request) {
        redeemCodeService.create(request);
        return RespBody.success();
    }

    @RequestMapping("/update")
    @ApiOperation("更新")
    public RespBody<Void> update(@Validated @RequestBody RedeemCodeEditRequest request) {
        redeemCodeService.update(request);
        return RespBody.success();
    }

    @RequestMapping("/delete")
    @ApiOperation("删除")
    public RespBody<Void> delete(@RequestBody @Validated IdDTO dto) {
        redeemCodeService.delete(dto.getId());
        return RespBody.success();
    }

    @RequestMapping("/grant/listPage")
    @ApiOperation("列表")
    public RespBody<PageData<RedeemCodeGrantResponse>> grantListPage(RedeemCodeGrantQueryRequest request) {
        Page<RedeemCodeGrantResponse> listPage = redeemCodeGrantService.listPage(request);
        return RespBody.success(PageData.toPage(listPage));
    }

    @RequestMapping("/grant/export")
    @ApiOperation("导出")
    public void grantExport(HttpServletResponse response, RedeemCodeGrantQueryRequest request) {
        List<RedeemCodeGrantResponse> byPage = redeemCodeGrantService.getList(request);
        ExcelUtil.export(response, "兑换码", byPage, RedeemCodeGrantResponse.class);
    }
}
