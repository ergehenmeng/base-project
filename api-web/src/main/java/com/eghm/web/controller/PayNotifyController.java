package com.eghm.web.controller;

import com.eghm.service.pay.PayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 二哥很猛
 * @date 2022/7/25
 */
@RestController
@Api(tags = "支付回调管理")
@AllArgsConstructor
public class PayNotifyController {

    private final PayService aliPayService;

    private final PayService wechatPayService;

    @PostMapping("/notify/ali/pay")
    @ApiOperation("支付宝回调")
    public void aliPay(HttpServletRequest request) {
        Map<String, String> stringMap = parseRequest(request);
        boolean notify = aliPayService.verifyNotify(stringMap);
    }

    @PostMapping("/notify/ali/refund")
    @ApiOperation("支付宝退款回调")
    public void aliRefund(HttpServletRequest request) {
        Map<String, String> stringMap = parseRequest(request);
        boolean notify = aliPayService.verifyNotify(stringMap);
    }

    @PostMapping("/notify/weChat/pay")
    @ApiOperation("微信支付回调")
    public void weChatPay(HttpServletRequest request) {

    }

    @PostMapping("/notify/weChat/refund")
    @ApiOperation("微信退款回调")
    public void weChatRefund(HttpServletRequest request) {

    }

    /**
     * 解析请求参数
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
