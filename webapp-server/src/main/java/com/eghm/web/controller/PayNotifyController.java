package com.eghm.web.controller;

import com.eghm.constant.WeChatConstant;
import com.eghm.service.business.CommonService;
import com.eghm.service.business.handler.dto.PayNotifyContext;
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

    @PostMapping("${system.ali-pay.pay-notify-url:/notify/ali/pay}")
    @ApiOperation("支付宝回调")
    public String aliPay(HttpServletRequest request) {
        Map<String, String> stringMap = parseRequest(request);
        aliPayService.verifyNotify(stringMap);
        payNotifyLogService.insertAliLog(stringMap, NotifyType.PAY);
        String orderNo = stringMap.get("body");
        String outTradeNo = stringMap.get("out_trade_no");
        PayNotifyContext context = new PayNotifyContext();
        context.setOrderNo(orderNo);
        context.setOutTradeNo(outTradeNo);
        commonService.getPayHandler(orderNo).doAction(context);
        return ALI_PAY_SUCCESS;
    }

    @PostMapping("${system.ali-pay.refund-notify-url:/notify/ali/refund}")
    @ApiOperation("支付宝退款回调")
    public String aliRefund(HttpServletRequest request) {
        Map<String, String> stringMap = parseRequest(request);
        aliPayService.verifyNotify(stringMap);
        payNotifyLogService.insertAliLog(stringMap, NotifyType.REFUND);
        // TODO 业务
        return ALI_PAY_SUCCESS;
    }

    @PostMapping("${system.wechat.pay-notify-url:/notify/weChat/pay}")
    @ApiOperation("微信支付回调")
    public void weChatPay(@RequestHeader HttpHeaders httpHeader, @RequestBody String requestBody) {
        SignatureHeader header = this.parseHeader(httpHeader);
        WxPayOrderNotifyV3Result payNotify = wechatPayService.parsePayNotify(requestBody, header);
        payNotifyLogService.insertWechatPayLog(payNotify);
        // TODO 业务

    }

    @PostMapping("${system.wechat.refund-notify-url:/notify/weChat/refund}")
    @ApiOperation("微信退款回调")
    public void weChatRefund(@RequestHeader HttpHeaders httpHeader, @RequestBody String requestBody) {
        SignatureHeader header = this.parseHeader(httpHeader);
        WxPayRefundNotifyV3Result payNotify = wechatPayService.parseRefundNotify(requestBody, header);
        payNotifyLogService.insertWechatRefundLog(payNotify);
        // TODO 业务
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
