package com.eghm.web.controller;

import com.eghm.configuration.SystemProperties;
import com.eghm.dto.ext.RespBody;
import com.eghm.logistics.service.ExpressService;
import com.eghm.vo.business.order.item.ExpressVO;
import com.google.gson.Gson;
import com.kuaidi100.sdk.response.SubscribePushData;
import com.kuaidi100.sdk.response.SubscribePushParamResp;
import com.kuaidi100.sdk.response.SubscribePushResult;
import com.kuaidi100.sdk.response.SubscribeResp;
import com.kuaidi100.sdk.utils.SignUtils;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2025/5/29
 */
@RestController
@RequestMapping("/express/notify")
@AllArgsConstructor
public class ExpressController {

    private final ExpressService expressService;

    private final SystemProperties systemProperties;

    @PostMapping("/subscribe")
    @ApiOperation(value = "快递订阅")
    public RespBody<Void> subscribe(String expressNo, String expressCode, String phone) {
        expressService.subscribe(expressNo, expressCode, phone);
        return RespBody.success();
    }

    @GetMapping("/query")
    @ApiOperation(value = "快递查询")
    public RespBody<List<ExpressVO>> query(String expressNo, String expressCode, String phone) {
        List<ExpressVO> expressList = expressService.getExpressList(expressNo, expressCode, phone);
        return RespBody.success(expressList);
    }

    @PostMapping("/callback")
    @ApiOperation(value = "快递物流回调")
    public SubscribeResp callback(HttpServletRequest request) {
        SubscribePushParamResp paramResp = this.parseParam(request);
        if (paramResp == null) {
            SubscribeResp subscribeResp = new SubscribeResp();
            subscribeResp.setResult(Boolean.FALSE);
            subscribeResp.setReturnCode("500");
            subscribeResp.setMessage("ERROR");
            return subscribeResp;
        }
        SubscribePushResult lastResult = paramResp.getLastResult();
        List<SubscribePushData> dataList = lastResult.getData();
        expressService.updateExpress(lastResult.getNu(), dataList);
        SubscribeResp subscribeResp = new SubscribeResp();
        subscribeResp.setResult(Boolean.TRUE);
        subscribeResp.setReturnCode("200");
        subscribeResp.setMessage("SUCCESS");
        return subscribeResp;
    }

    /**
     * 解析快递100回调
     *
     * @param request request
     * @return 请求参数
     */
    public SubscribePushParamResp parseParam(HttpServletRequest request) {
        String param = request.getParameter("param");
        String sign = request.getParameter("sign");
        String salt = systemProperties.getExpress().getSalt();
        String ourSign = SignUtils.sign(param + salt);
        //加密如果相等，属于快递100推送；否则可以忽略掉当前请求
        if (ourSign.equals(sign)) {
            return new Gson().fromJson(param, SubscribePushParamResp.class);
        }
        return null;
    }
}
