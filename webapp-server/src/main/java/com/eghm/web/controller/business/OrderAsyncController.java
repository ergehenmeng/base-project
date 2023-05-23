package com.eghm.web.controller.business;

import cn.hutool.core.util.StrUtil;
import com.eghm.constant.CacheConstant;
import com.eghm.constant.CommonConstant;
import com.eghm.dto.ext.RespBody;
import com.eghm.enums.ErrorCode;
import com.eghm.service.cache.CacheService;
import com.eghm.vo.order.OrderVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
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
    @ApiOperation("异步查询下单结果")
    @ApiImplicitParam(name = "key", value = "查询key", required = true)
    public RespBody<OrderVO<String>> getResult(@RequestParam("key") String key) {
        String hashValue = cacheService.getValue(CacheConstant.MQ_ASYNC_KEY + key);
        OrderVO<String> vo = new OrderVO<>();
        if (hashValue != null && CacheConstant.PLACE_HOLDER.startsWith(hashValue)) {
            this.setProcessResult(key, hashValue, vo);
            return RespBody.success(vo);
        }
        // 下单成功将订单号返回给前端
        if (CacheConstant.SUCCESS_PLACE_HOLDER.equals(hashValue)) {
            vo.setState(1);
            vo.setData(cacheService.getValue(CacheConstant.MQ_ASYNC_DATA_KEY + key));
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

    @GetMapping("/cancel")
    @ApiOperation("异步下单取消")
    @ApiImplicitParam(name = "key", value = "查询的key", required = true)
    public RespBody<Void> cancel(@RequestParam("key") String key) {
        String redisKey = CacheConstant.MQ_ASYNC_KEY + key;
        boolean hashValue = cacheService.exist(redisKey);
        if (hashValue) {
            cacheService.setValue(redisKey, CacheConstant.PLACE_HOLDER + CommonConstant.MAX_ACCESS_NUM);
        }
        return RespBody.success();
    }

    /**
     * 组装处理中的返回值结果
     * 注意: 前端不能无限制查询结果, 有最大次数限制, 因此需要保留前端访问的次数, 超过次数后默认下单失败;
     * 存储方式:  @0 @1 @2 前一位表示处理中, 后面表示访问的次数
     * @param key   查询的key
     * @param hashValue  查询的结果
     * @param vo 结果信息存放
     */
    private void setProcessResult(String key, String hashValue, OrderVO<String> vo) {
        String accessStr = hashValue.replace(CacheConstant.PLACE_HOLDER, "");
        int accessNum = 0;
        if (StrUtil.isNotBlank(accessStr)) {
            accessNum = Integer.parseInt(accessStr);
            // 超过最大请求次数, 订单还在队列中处理,默认直接提示商品火爆
            if (accessNum >= CommonConstant.MAX_ACCESS_NUM) {
                vo.setState(2);
                vo.setErrorMsg(ErrorCode.ORDER_ERROR.getMsg());
                return;
            }
        }
        cacheService.setValue(CacheConstant.MQ_ASYNC_KEY + key, CacheConstant.PLACE_HOLDER + (++accessNum));
        vo.setState(1);
        vo.setData(key);
    }

}
