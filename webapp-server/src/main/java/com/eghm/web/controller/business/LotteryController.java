package com.eghm.web.controller.business;

import com.eghm.constant.CacheConstant;
import com.eghm.dto.IdDTO;
import com.eghm.dto.ext.ApiHolder;
import com.eghm.dto.ext.RespBody;
import com.eghm.enums.ErrorCode;
import com.eghm.service.business.lottery.LotteryService;
import com.eghm.service.cache.RedisLock;
import com.eghm.vo.business.lottery.LotteryDetailVO;
import com.eghm.vo.business.lottery.LotteryResultVO;
import com.eghm.vo.business.lottery.LotteryVO;
import com.eghm.web.annotation.AccessToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2023/8/2
 */
@RestController
@Api(tags = "抽奖")
@AllArgsConstructor
@RequestMapping("/webapp/lottery")
public class LotteryController {

    private final LotteryService lotteryService;

    private final RedisLock redisLock;

    @GetMapping("/list")
    @ApiOperation("抽奖列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "merchantId", value = "商户Id", required = true),
            @ApiImplicitParam(name = "storeId", value = "店铺id", required = true)
    })
    public RespBody<List<LotteryVO>> list(@RequestParam("merchantId") Long merchantId,
                                          @RequestParam("storeId") Long storeId) {
        List<LotteryVO> voList = lotteryService.getList(merchantId, storeId);
        return RespBody.success(voList);
    }

    @GetMapping("/detail")
    @ApiOperation("抽奖详情")
    public RespBody<LotteryDetailVO> detail(@Validated IdDTO dto) {
        Long memberId = ApiHolder.tryGetMemberId();
        LotteryDetailVO detail = lotteryService.detail(dto.getId(), memberId);
        return RespBody.success(detail);
    }

    @PostMapping("/handle")
    @ApiOperation("抽奖")
    @AccessToken
    public RespBody<LotteryResultVO> handle(@RequestBody @Validated IdDTO dto) {
        String key = String.format(CacheConstant.LOTTERY_LOCK, dto.getId(), ApiHolder.getMemberId());
        return redisLock.lock(key, 30,
                () -> RespBody.success(lotteryService.lottery(dto.getId(), ApiHolder.getMemberId())),
                () -> RespBody.error(ErrorCode.LOTTERY_EXECUTE));
    }
}
