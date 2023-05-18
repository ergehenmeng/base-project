package com.eghm.web.controller.business;

import com.eghm.constant.CacheConstant;
import com.eghm.dto.ext.RespBody;
import com.eghm.enums.ErrorCode;
import com.eghm.service.cache.CacheService;
import com.eghm.vo.order.OrderResultVO;
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
        if (CacheConstant.PLACE_HOLDER.equals(hashValue)) {
            vo.setState(0);
            return RespBody.success(vo);
        }
        if (CacheConstant.SUCCESS_PLACE_HOLDER.equals(hashValue)) {
            vo.setState(1);
            return RespBody.success(vo);
        }
        if (!CacheConstant.ERROR_PLACE_HOLDER.equals(hashValue)) {
            vo.setState(2);
            vo.setErrorMsg(hashValue);
            return RespBody.success(vo);
        }
        vo.setState(2);
        vo.setErrorMsg(ErrorCode.ORDER_ERROR.getMsg());
        return RespBody.success(vo);

    }
}
