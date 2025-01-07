package com.eghm.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.dto.IdDTO;
import com.eghm.dto.business.lottery.LotteryAddRequest;
import com.eghm.dto.business.lottery.LotteryEditRequest;
import com.eghm.dto.business.lottery.LotteryLotQueryRequest;
import com.eghm.dto.business.lottery.LotteryQueryRequest;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.service.business.lottery.LotteryLogService;
import com.eghm.service.business.lottery.LotteryService;
import com.eghm.vo.business.lottery.LotteryDetailResponse;
import com.eghm.vo.business.lottery.LotteryLogResponse;
import com.eghm.vo.business.lottery.LotteryResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author 二哥很猛
 * @since 2023/8/2
 */
@RestController
@Tag(name="抽奖配置")
@AllArgsConstructor
@RequestMapping(value = "/manage/lottery", produces = MediaType.APPLICATION_JSON_VALUE)
public class LotteryController {

    private final LotteryService lotteryService;

    private final LotteryLogService lotteryLogService;

    @GetMapping("/listPage")
    @Operation(summary = "列表")
    public RespBody<PageData<LotteryResponse>> listPage(@Validated LotteryQueryRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        Page<LotteryResponse> merchantPage = lotteryService.getByPage(request);
        return RespBody.success(PageData.toPage(merchantPage));
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "新增")
    public RespBody<Void> create(@RequestBody @Validated LotteryAddRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        lotteryService.create(request);
        return RespBody.success();
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "更新")
    public RespBody<Void> update(@RequestBody @Validated LotteryEditRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        lotteryService.update(request);
        return RespBody.success();
    }

    @GetMapping("/detail")
    @Operation(summary = "详情")
    public RespBody<LotteryDetailResponse> detail(@Validated IdDTO dto) {
        LotteryDetailResponse response = lotteryService.getDetailById(dto.getId());
        return RespBody.success(response);
    }

    @PostMapping(value = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "删除")
    public RespBody<Void> delete(@RequestBody @Validated IdDTO request) {
        lotteryService.delete(request.getId());
        return RespBody.success();
    }

    @GetMapping("/logPage")
    @Operation(summary = "抽奖记录")
    public RespBody<PageData<LotteryLogResponse>> logPage(@Validated LotteryLotQueryRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        Page<LotteryLogResponse> byPage = lotteryLogService.getByPage(request);
        return RespBody.success(PageData.toPage(byPage));
    }
}
