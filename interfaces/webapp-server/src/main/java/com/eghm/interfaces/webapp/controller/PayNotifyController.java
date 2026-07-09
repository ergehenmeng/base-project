package com.eghm.interfaces.webapp.controller;

import com.eghm.constants.WeChatConstant;
import com.eghm.domain.shared.exception.BusinessException;
import com.eghm.application.payment.dto.PayNotifyMessage;
import com.eghm.application.payment.service.PayNotifyLogService;
import com.eghm.application.payment.service.PayService;
import com.eghm.domain.payment.enums.StepType;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.Map;

import static com.eghm.constants.CommonConstant.*;

/**
 * @author 二哥很猛
 * @since 2022/7/25
 */
@RestController
@Tag(name = "支付回调管理")
@AllArgsConstructor
@Slf4j
public class PayNotifyController {

    private final PayService aliPayService;

    private final PayService wechatPayService;

    private final PayNotifyLogService payNotifyLogService;

    @PostMapping(ALI_PAY_NOTIFY_URL)
    @Operation(summary = "支付宝支付回调")
    public String aliPay(HttpServletRequest request) {
        Map<String, String> stringMap = this.parseAliRequest(request);
        aliPayService.verifyNotify(stringMap);
        payNotifyLogService.insertAliLog(stringMap, StepType.PAY);
        String tradeNo = stringMap.get("out_trade_no");
        // 不以第三方返回的状态为准, 而是通过接口查询订单状态
        return this.aliResult(() -> log.error("支付宝支付回调成功, 请补全逻辑, 本地支付单号:[{}]", tradeNo));
    }

    @PostMapping(ALI_REFUND_NOTIFY_URL)
    @Operation(summary = "支付宝退款回调")
    public String aliRefund(HttpServletRequest request) {
        Map<String, String> stringMap = this.parseAliRequest(request);
        aliPayService.verifyNotify(stringMap);
        payNotifyLogService.insertAliLog(stringMap, StepType.REFUND);
        String refundNo = stringMap.get("out_biz_no");
        String tradeNo = stringMap.get("out_trade_no");
        // 不以第三方返回的状态为准, 而是通过接口查询订单状态
        return this.aliResult(() -> log.error("支付宝退款回调成功, 请补全逻辑, 本地退款单号:[{}] 本地支付单号:[{}]", refundNo, tradeNo));
    }

    @PostMapping(WECHAT_PAY_NOTIFY_URL)
    @Operation(summary = "微信支付回调")
    public Map<String, String> weChatPay(@RequestHeader HttpHeaders httpHeader, @RequestBody String requestBody, HttpServletResponse response) {
        Map<String, String> header = this.parseWechatHeader(httpHeader);
        PayNotifyMessage payNotify = wechatPayService.parsePayNotify(requestBody, header);
        payNotifyLogService.insertWechatPayLog(payNotify);
        // 不以第三方返回的状态为准, 而是通过接口查询订单状态
        return this.wechatResult(response, () -> log.error("微信支付回调成功, 请补全逻辑, 本地支付单号:[{}] ", payNotify.getTradeNo()));
    }

    @PostMapping(WECHAT_REFUND_NOTIFY_URL)
    @Operation(summary = "微信退款回调")
    public Map<String, String> weChatRefund(@RequestHeader HttpHeaders httpHeader, @RequestBody String requestBody, HttpServletResponse response) {
        Map<String, String> header = this.parseWechatHeader(httpHeader);
        PayNotifyMessage payNotify = wechatPayService.parseRefundNotify(requestBody, header);
        payNotifyLogService.insertWechatRefundLog(payNotify);
        String refundNo = payNotify.getRefundNo();
        String tradeNo = payNotify.getTradeNo();
        return this.wechatResult(response, () -> log.error("微信退款回调成功, 请补全逻辑, 本地退款单号:[{}] 本地支付单号:[{}]", refundNo, tradeNo));
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
    private Map<String, String> parseWechatHeader(HttpHeaders headers) {
        Map<String, String> header = new HashMap<>(4);
        header.put(WeChatConstant.SIGNATURE, headers.getFirst(WeChatConstant.SIGNATURE));
        header.put(WeChatConstant.TIMESTAMP, headers.getFirst(WeChatConstant.TIMESTAMP));
        header.put(WeChatConstant.SERIAL, headers.getFirst(WeChatConstant.SERIAL));
        header.put(WeChatConstant.NONCE, headers.getFirst(WeChatConstant.NONCE));
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
