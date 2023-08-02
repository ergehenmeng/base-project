package com.eghm.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.lottery.LotteryAddRequest;
import com.eghm.dto.business.lottery.LotteryEditRequest;
import com.eghm.dto.business.lottery.LotteryQueryRequest;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.service.business.lottery.LotteryService;
import com.eghm.vo.business.lottery.LotteryResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author 二哥很猛
 * @since 2023/8/2
 */
@RestController
@Api(tags = "抽奖配置")
@AllArgsConstructor
@RequestMapping("/manage/lottery")
public class LotteryController {

    private final LotteryService lotteryService;

    @GetMapping("/listPage")
    @ApiOperation("商户列表")
    public RespBody<PageData<LotteryResponse>> listPage(@Validated LotteryQueryRequest request) {
        Page<LotteryResponse> merchantPage = lotteryService.getByPage(request);
        return RespBody.success(PageData.toPage(merchantPage));
    }

    @PostMapping("/create")
    @ApiOperation("创建抽奖配置")
    public RespBody<Void> create(@RequestBody @Validated LotteryAddRequest request) {
        lotteryService.create(request);
        return RespBody.success();
    }

    @PostMapping("/update")
    @ApiOperation("更新抽奖配置")
    public RespBody<Void> update(@RequestBody @Validated LotteryEditRequest request) {
        lotteryService.update(request);
        return RespBody.success();
    }

}
