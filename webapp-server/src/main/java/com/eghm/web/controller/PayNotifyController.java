package com.eghm.web.controller;

import com.eghm.constant.CacheConstant;
import com.eghm.constant.WeChatConstant;
import com.eghm.service.business.CommonService;
import com.eghm.service.business.handler.dto.PayNotifyContext;
import com.eghm.service.business.handler.dto.RefundNotifyContext;
import com.eghm.service.cache.LockService;
import com.eghm.service.pay.PayNotifyLogService;
import com.eghm.service.pay.PayService;
import com.eghm.service.pay.enums.NotifyType;
import com.github.binarywang.wxpay.bean.notify.SignatureHeader;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyV3Result;
import com.github.binarywang.wxpay.bean.notify.WxPayRefundNotifyV3Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import static com.eghm.constant.CommonConstant.ALI_PAY_SUCCESS;

/**
 * @author 二哥很猛
 * @date 2022/7/25
 */
@RestController
@Api(tags = "支付回调管理")
@AllArgsConstructor
@Slf4j
@RequestMapping("/webapp")
public class PayNotifyController {

    private final PayService aliPayService;

    private final PayService wechatPayService;

    private final PayNotifyLogService payNotifyLogService;

    private final CommonService commonService;

    private final LockService lockService;

    @PostMapping("${system.ali-pay.pay-notify-url:/notify/ali/pay}")
    @ApiOperation("支付宝支付回调")
    public String aliPay(HttpServletRequest request) {
        Map<String, String> stringMap = parseRequest(request);
        aliPayService.verifyNotify(stringMap);
        payNotifyLogService.insertAliLog(stringMap, NotifyType.PAY);
        String orderNo = stringMap.get("body");
        String outTradeNo = stringMap.get("out_trade_no");
        // 不以第三方返回的状态为准, 而是通过接口查询订单状态
        PayNotifyContext context = new PayNotifyContext();
        context.setOrderNo(orderNo);
        context.setOutTradeNo(outTradeNo);
        return lockService.lock(CacheConstant.ALI_PAY_NOTIFY_LOCK + orderNo, 10_000, () -> {
            commonService.getPayHandler(orderNo).doAction(context);
            return ALI_PAY_SUCCESS;
        });
    }

    @PostMapping("${system.ali-pay.refund-notify-url:/notify/ali/refund}")
    @ApiOperation("支付宝退款回调")
    public String aliRefund(HttpServletRequest request) {
        Map<String, String> stringMap = parseRequest(request);
        aliPayService.verifyNotify(stringMap);
        payNotifyLogService.insertAliLog(stringMap, NotifyType.REFUND);
        String outRefundNo = stringMap.get("out_biz_no");
        String outTradeNo = stringMap.get("out_trade_no");
        // 不以第三方返回的状态为准, 而是通过接口查询订单状态
        RefundNotifyContext context = new RefundNotifyContext();
        context.setOutRefundNo(outRefundNo);
        context.setOutTradeNo(outTradeNo);
        return lockService.lock(CacheConstant.ALI_REFUND_NOTIFY_LOCK + outTradeNo, 10_000, () -> {
            commonService.getRefundHandler(outRefundNo).doAction(context);
            return ALI_PAY_SUCCESS;
        });
    }

    @PostMapping("${system.wechat.pay-notify-url:/notify/weChat/pay}")
    @ApiOperation("微信支付回调")
    public void weChatPay(@RequestHeader HttpHeaders httpHeader, @RequestBody String requestBody) {
        SignatureHeader header = this.parseHeader(httpHeader);
        WxPayOrderNotifyV3Result payNotify = wechatPayService.parsePayNotify(requestBody, header);
        payNotifyLogService.insertWechatPayLog(payNotify);
        // 不以第三方返回的状态为准, 而是通过接口查询订单状态
        String orderNo = payNotify.getResult().getAttach();
        PayNotifyContext context = new PayNotifyContext();
        context.setOrderNo(orderNo);
        context.setOutTradeNo(payNotify.getResult().getOutTradeNo());
        lockService.lock(CacheConstant.WECHAT_PAY_NOTIFY_LOCK + orderNo, 10_000, () -> {
            commonService.getPayHandler(orderNo).doAction(context);
            return null;
        });
    }

    @PostMapping("${system.wechat.refund-notify-url:/notify/weChat/refund}")
    @ApiOperation("微信退款回调")
    public void weChatRefund(@RequestHeader HttpHeaders httpHeader, @RequestBody String requestBody) {
        SignatureHeader header = this.parseHeader(httpHeader);
        WxPayRefundNotifyV3Result payNotify = wechatPayService.parseRefundNotify(requestBody, header);
        payNotifyLogService.insertWechatRefundLog(payNotify);
        String outRefundNo = payNotify.getResult().getOutRefundNo();
        String outTradeNo = payNotify.getResult().getOutTradeNo();
        // 不以第三方返回的状态为准, 而是通过接口查询订单状态
        RefundNotifyContext context = new RefundNotifyContext();
        context.setOutRefundNo(outRefundNo);
        context.setOutTradeNo(outTradeNo);
        lockService.lock(CacheConstant.WECHAT_REFUND_NOTIFY_LOCK + outTradeNo, 10_000, () -> {
            commonService.getRefundHandler(outRefundNo).doAction(context);
            return null;
        });
    }

    /**
     * 解析微信请求头信息
     * @param headers 请求头
     * @return 验签对象
     */
    private SignatureHeader parseHeader(HttpHeaders headers) {
        SignatureHeader header = new SignatureHeader();
        header.setSignature(headers.getFirst(WeChatConstant.SIGNATURE));
        header.setTimeStamp(headers.getFirst(WeChatConstant.TIMESTAMP));
        header.setSerial(headers.getFirst(WeChatConstant.SERIAL));
        header.setNonce(headers.getFirst(WeChatConstant.NONCE));
        return header;
    }

    /**
     * 解析支付宝请求参数
     * @param request 请求参数
     * @return 解析后的参数
     */
    public static Map<String, String> parseRequest(HttpServletRequest request) {
        Map< String , String > params = new HashMap<>();
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
