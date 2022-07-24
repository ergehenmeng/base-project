package com.eghm.service.pay.impl;

import com.eghm.service.pay.PayService;
import com.eghm.service.pay.dto.PrepayDTO;
import com.eghm.service.pay.dto.RefundDTO;
import com.eghm.service.pay.vo.OrderVO;
import com.eghm.service.pay.vo.PrepayVO;
import com.eghm.service.pay.vo.RefundVO;
import com.github.binarywang.wxpay.bean.notify.SignatureHeader;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyV3Result;
import com.github.binarywang.wxpay.bean.notify.WxPayRefundNotifyV3Result;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @date 2022/7/24
 */
@Service("aliPayService")
public class AliPayServiceImpl implements PayService {

    @Override
    public PrepayVO createPrepay(PrepayDTO dto) {
        return null;
    }

    @Override
    public OrderVO queryOrder(String outTradeNo) {
        return null;
    }

    @Override
    public void closeOrder(String outTradeNo) {

    }

    @Override
    public RefundVO applyRefund(RefundDTO dto) {
        return null;
    }

    @Override
    public RefundVO queryRefund(String outTradeNo) {
        return null;
    }

    @Override
    public WxPayOrderNotifyV3Result parsePayNotify(String notifyData, SignatureHeader header) {
        return null;
    }

    @Override
    public WxPayRefundNotifyV3Result parseRefundNotify(String notifyData, SignatureHeader header) {
        return null;
    }
}
