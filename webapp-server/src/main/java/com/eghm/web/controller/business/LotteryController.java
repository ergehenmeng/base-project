package com.eghm.web.controller.business;

import com.eghm.constant.CacheConstant;
import com.eghm.dto.IdDTO;
import com.eghm.dto.ext.ApiHolder;
import com.eghm.dto.ext.RespBody;
import com.eghm.enums.ErrorCode;
import com.eghm.service.business.lottery.LotteryService;
import com.eghm.service.cache.RedisLock;
import com.eghm.vo.business.lottery.LotteryResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/handle")
    @ApiOperation("抽奖")
    public RespBody<LotteryResultVO> handle(@RequestBody @Validated IdDTO dto) {
        String key = String.format(CacheConstant.LOTTERY_LOCK, dto.getId(), ApiHolder.getMemberId());
        return redisLock.lock(key, 30,
                () -> RespBody.success(lotteryService.lottery(dto.getId(), ApiHolder.getMemberId())),
                () -> RespBody.error(ErrorCode.LOTTERY_EXECUTE));
    }
}
