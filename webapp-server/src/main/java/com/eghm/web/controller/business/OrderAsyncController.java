package com.eghm.web.controller.business;

import com.eghm.common.constant.CacheConstant;
import com.eghm.common.enums.ErrorCode;
import com.eghm.model.dto.ext.RespBody;
import com.eghm.model.vo.order.OrderResultVO;
import com.eghm.service.cache.CacheService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 二哥很猛
 * @since 2022/9/28
 */
@RestController
@Api(tags = "订单异步查询")
@AllArgsConstructor
@RequestMapping("/webapp/order/async")
public class OrderAsyncController {

    private final CacheService cacheService;

    @GetMapping("/result")
    @ApiImplicitParam(name = "key", value = "查询key", required = true)
    public RespBody<OrderResultVO> getResult(@RequestParam("key") String key) {
        String hashValue = cacheService.getValue(CacheConstant.MQ_ASYNC_KEY + key);
        OrderResultVO vo = new OrderResultVO();
        // 只有排队超过最大时间时, key过期才会出现为空
        if (hashValue == null) {
            vo.setState(2);
            return RespBody.success(vo);
        }

        if (CacheConstant.PLACE_HOLDER.equals(hashValue)) {
            vo.setState(0);
            return RespBody.success(vo);
        }

        if (CacheConstant.SUCCESS_PLACE_HOLDER.equals(hashValue)) {
            vo.setState(1);
            return RespBody.success(vo);
        }

        if (CacheConstant.ERROR_PLACE_HOLDER.equals(hashValue)) {
            vo.setState(2);
            vo.setErrorMsg(ErrorCode.SYSTEM_ERROR.getMsg());
            return RespBody.success(vo);
        }

        // 业务异常
        vo.setState(2);
        vo.setErrorMsg(hashValue);
        return RespBody.success(vo);
    }
}
