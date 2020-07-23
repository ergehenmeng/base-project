package com.eghm.interceptor;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.MD5;
import com.eghm.common.constant.CommonConstant;
import com.eghm.common.enums.ErrorCode;
import com.eghm.common.exception.ParameterException;
import com.eghm.constants.ConfigConstant;
import com.eghm.model.ext.RequestMessage;
import com.eghm.model.ext.RequestThreadLocal;
import com.eghm.service.system.impl.SystemConfigApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 签名验证(建议https+签名+数据加密)
 * @author 殿小二
 * @date 2020/7/23
 */
@Slf4j
public class SignatureInterceptor implements InterceptorAdapter {

    private SystemConfigApi systemConfigApi;

    @Autowired
    public void setSystemConfigApi(SystemConfigApi systemConfigApi) {
        this.systemConfigApi = systemConfigApi;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!this.supportHandler(handler)) {
            return true;
        }
        RequestMessage message = RequestThreadLocal.get();
        if (StrUtil.isBlank(message.getTimestamp())) {
            throw new ParameterException(ErrorCode.SIGNATURE_TIMESTAMP_NULL);
        }
        long clientTimestamp = Long.parseLong(message.getTimestamp());
        long systemTimestamp = System.currentTimeMillis();
        long deviation = systemConfigApi.getLong(ConfigConstant.TIMESTAMP_DEVIATION);
        // 服务端时间误差
        if (Math.abs(systemTimestamp - clientTimestamp) > deviation) {
            log.warn("客户端服务端时间误差:[{}]", Math.abs(systemTimestamp - clientTimestamp));
            throw new ParameterException(ErrorCode.SIGNATURE_TIMESTAMP_ERROR);
        }
        if (StrUtil.isNotBlank(message.getRequestBody())) {
            String digestHex = MD5.create().digestHex(message.getTimestamp() + Base64.decodeStr(message.getRequestBody(), CommonConstant.CHARSET));
            if (!digestHex.equals(message.getSignature())) {
                log.warn("签名信息校验失败 signature:[{}], digestHex:[{}]", message.getSignature(), digestHex);
                throw new ParameterException(ErrorCode.SIGNATURE_VERIFY_ERROR);
            }
        }
        return false;
    }
}
