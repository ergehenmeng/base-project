package com.eghm.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.IdDTO;
import com.eghm.dto.business.redeem.RedeemCodeAddRequest;
import com.eghm.dto.business.redeem.RedeemCodeEditRequest;
import com.eghm.dto.business.redeem.RedeemCodeQueryRequest;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.model.RedeemCode;
import com.eghm.service.business.RedeemCodeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
