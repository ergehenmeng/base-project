package com.eghm.web.controller;

import com.eghm.constant.CacheConstant;
import com.eghm.constant.WeChatConstant;
import com.eghm.dto.ext.RespBody;
import com.eghm.enums.ref.ProductType;
import com.eghm.exception.BusinessException;
import com.eghm.service.business.CommonService;
import com.eghm.service.business.ScanRechargeLogService;
import com.eghm.service.business.handler.access.AbstractAccessHandler;
import com.eghm.service.business.handler.context.PayNotifyContext;
import com.eghm.service.business.handler.context.RefundNotifyContext;
import com.eghm.service.cache.RedisLock;
import com.eghm.service.pay.PayNotifyLogService;
import com.eghm.service.pay.PayService;
import com.eghm.service.pay.enums.StepType;
import com.eghm.service.pay.enums.TradeType;
import com.github.binarywang.wxpay.bean.notify.SignatureHeader;
import com.github.binarywang.wxpay.bean.notify.WxPayNotifyV3Result;
import com.github.binarywang.wxpay.bean.notify.WxPayRefundNotifyV3Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static com.eghm.constant.CommonConstant.*;

/**
 * @author 二哥很猛
 * @since 2022/7/25
 */
@RestController
@Api(tags = "支付回调管理")
@AllArgsConstructor
@Slf4j
public class PayNotifyController {

    private final RedisLock redisLock;

    private final PayService aliPayService;

    private final CommonService commonService;

    private final PayService wechatPayService;

    private final PayNotifyLogService payNotifyLogService;

    private final ScanRechargeLogService scanRechargeLogService;

    @PostMapping(ALI_PAY_NOTIFY_URL)
    @ApiOperation("支付宝支付回调")
    public String aliPay(HttpServletRequest request) {
        Map<String, String> stringMap = this.parseAliRequest(request);
        aliPayService.verifyNotify(stringMap);
        payNotifyLogService.insertAliLog(stringMap, StepType.PAY);

        String orderNo = stringMap.get("body");
        String tradeNo = stringMap.get("out_trade_no");
        // 不以第三方返回的状态为准, 而是通过接口查询订单状态
        PayNotifyContext context = new PayNotifyContext();
        context.setOrderNo(orderNo);
        context.setTradeNo(tradeNo);

        return this.aliResult(() -> redisLock.lockVoid(CacheConstant.ALI_PAY_NOTIFY_LOCK + orderNo, 10_000,
                () -> this.handlePayNotify(context)));
    }

    @PostMapping(ALI_REFUND_NOTIFY_URL)
    @ApiOperation("支付宝退款回调")
    public String aliRefund(HttpServletRequest request) {
        Map<String, String> stringMap = this.parseAliRequest(request);
        aliPayService.verifyNotify(stringMap);
        payNotifyLogService.insertAliLog(stringMap, StepType.REFUND);

        String refundNo = stringMap.get("out_biz_no");
        String tradeNo = stringMap.get("out_trade_no");
        // 不以第三方返回的状态为准, 而是通过接口查询订单状态
        RefundNotifyContext context = new RefundNotifyContext();
        context.setRefundNo(refundNo);
        context.setTradeNo(tradeNo);

        return this.aliResult(() -> redisLock.lockVoid(CacheConstant.ALI_REFUND_NOTIFY_LOCK + tradeNo, 10_000,
                () -> commonService.handleRefundNotify(context)));
    }

    @PostMapping(WECHAT_PAY_NOTIFY_URL)
    @ApiOperation("微信支付回调")
    public Map<String, String> weChatPay(@RequestHeader HttpHeaders httpHeader, @RequestBody String requestBody, HttpServletResponse response) {
        SignatureHeader header = this.parseWechatHeader(httpHeader);
        WxPayNotifyV3Result payNotify = wechatPayService.parsePayNotify(requestBody, header);
        payNotifyLogService.insertWechatPayLog(payNotify);

        // 不以第三方返回的状态为准, 而是通过接口查询订单状态
        String orderNo = payNotify.getResult().getAttach();
        PayNotifyContext context = new PayNotifyContext();
        context.setOrderNo(orderNo);
        context.setTradeNo(payNotify.getResult().getOutTradeNo());

        return this.wechatResult(response, () -> redisLock.lockVoid(CacheConstant.WECHAT_PAY_NOTIFY_LOCK + orderNo, 10_000,
                () -> this.handlePayNotify(context)));
    }

    @PostMapping(WECHAT_REFUND_NOTIFY_URL)
    @ApiOperation("微信退款回调")
    public Map<String, String> weChatRefund(@RequestHeader HttpHeaders httpHeader, @RequestBody String requestBody, HttpServletResponse response) {
        SignatureHeader header = this.parseWechatHeader(httpHeader);
        WxPayRefundNotifyV3Result payNotify = wechatPayService.parseRefundNotify(requestBody, header);
        payNotifyLogService.insertWechatRefundLog(payNotify);

        String refundNo = payNotify.getResult().getOutRefundNo();
        String tradeNo = payNotify.getResult().getOutTradeNo();
        // 不以第三方返回的状态为准, 而是通过接口查询订单状态
        RefundNotifyContext context = new RefundNotifyContext();
        context.setRefundNo(refundNo);
        context.setTradeNo(tradeNo);

        return this.wechatResult(response, () -> redisLock.lockVoid(CacheConstant.WECHAT_REFUND_NOTIFY_LOCK + tradeNo, 10_000,
                () -> commonService.handleRefundNotify(context)));
    }

    @PostMapping("/mockPaySuccess")
    @ApiOperation("模拟支付成功(测试)")
    public RespBody<Void> mockPaySuccess(@RequestParam("orderNo") String orderNo, @RequestParam("tradeNo") String tradeNo) {
        PayNotifyContext context = new PayNotifyContext();
        context.setOrderNo(orderNo);
        context.setTradeNo(tradeNo);
        context.setTradeType(TradeType.WECHAT_MINI);
        context.setSuccessTime(LocalDateTime.now());
        commonService.getHandler(orderNo, AbstractAccessHandler.class).paySuccess(context);
        return RespBody.success();
    }

    /**
     * 支付回调业务处理
     *
     * @param context 上下文
     */
    private void handlePayNotify(PayNotifyContext context) {
        ProductType productType = ProductType.ofPrefix(context.getOrderNo());
        if (productType != null) {
            log.info("开始进行商品支付回调异步处理 [{}]", context.getTradeNo());
            commonService.handlePayNotify(context);
        } else if (context.getTradeNo().startsWith(SCORE_RECHARGE_PREFIX)) {
            log.info("开始进行扫码充值回调异步处理 [{}]", context.getTradeNo());
            scanRechargeLogService.rechargeNotify(context);
        }
    }

    /**
     * 组装支付宝异步通知
     *
     * @param runnable 业务处理
     * @return 返回给支付宝的数据
     */
    private String aliResult(Runnable runnable) {
        try {
            runnable.run();
        } catch (BusinessException e) {
            return ALI_PAY_FAIL;
        } catch (Exception e) {
            log.error("支付宝异步通知业务处理异常", e);
            return ALI_PAY_FAIL;
        }
        return ALI_PAY_SUCCESS;
    }

    /**
     * 组装微信异步通知
     *
     * @param response response
     * @param runnable 业务处理
     * @return 返回给微信的数据
     */
    private Map<String, String> wechatResult(HttpServletResponse response, Runnable runnable) {
        Map<String, String> result = new HashMap<>(4);
        try {
            runnable.run();
            result.put("code", "SUCCESS");
        } catch (BusinessException e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            result.put("code", "FAIL");
            result.put("message", e.getMessage());
        } catch (Exception e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            result.put("code", "FAIL");
            result.put("message", "系统异常");
            log.error("微信异步通知业务处理异常", e);
        }
        return result;
    }

    /**
     * 解析微信请求头信息
     *
     * @param headers 请求头
     * @return 验签对象
     */
    private SignatureHeader parseWechatHeader(HttpHeaders headers) {
        SignatureHeader header = new SignatureHeader();
        header.setSignature(headers.getFirst(WeChatConstant.SIGNATURE));
        header.setTimeStamp(headers.getFirst(WeChatConstant.TIMESTAMP));
        header.setSerial(headers.getFirst(WeChatConstant.SERIAL));
        header.setNonce(headers.getFirst(WeChatConstant.NONCE));
        return header;
    }

    /**
     * 解析支付宝请求参数
     *
     * @param request 请求参数
     * @return 解析后的参数
     */
    private Map<String, String> parseAliRequest(HttpServletRequest request) {
        Map<String, String> params = new HashMap<>(32);
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Map.Entry<String, String[]> entry : requestParams.entrySet()) {
            String[] values = entry.getValue();
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            params.put(entry.getKey(), valueStr);
        }
        return params;
    }

}
