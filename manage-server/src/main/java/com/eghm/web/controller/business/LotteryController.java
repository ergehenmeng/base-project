package com.eghm.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.dto.IdDTO;
import com.eghm.dto.business.lottery.LotteryAddRequest;
import com.eghm.dto.business.lottery.LotteryEditRequest;
import com.eghm.dto.business.lottery.LotteryQueryRequest;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.service.business.lottery.LotteryService;
import com.eghm.vo.business.lottery.LotteryDetailResponse;
import com.eghm.vo.business.lottery.LotteryResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author 二哥很猛
 * @since 2023/8/2
 */
@RestController
@Api(tags = "抽奖配置")
@AllArgsConstructor
@RequestMapping(value = "/manage/lottery", produces = MediaType.APPLICATION_JSON_VALUE)
public class LotteryController {

    private final LotteryService lotteryService;

    @GetMapping("/listPage")
    @ApiOperation("列表")
    public RespBody<PageData<LotteryResponse>> listPage(@Validated LotteryQueryRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        Page<LotteryResponse> merchantPage = lotteryService.getByPage(request);
        return RespBody.success(PageData.toPage(merchantPage));
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("新增")
    public RespBody<Void> create(@RequestBody @Validated LotteryAddRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        lotteryService.create(request);
        return RespBody.success();
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("更新")
    public RespBody<Void> update(@RequestBody @Validated LotteryEditRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        lotteryService.update(request);
        return RespBody.success();
    }

    @GetMapping("/detail")
    @ApiOperation("详情")
    public RespBody<LotteryDetailResponse> detail(@Validated IdDTO dto) {
        LotteryDetailResponse response = lotteryService.getDetailById(dto.getId());
        return RespBody.success(response);
    }
}
