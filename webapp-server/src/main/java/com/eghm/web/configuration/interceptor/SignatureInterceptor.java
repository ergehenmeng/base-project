package com.eghm.web.configuration.interceptor;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.MD5;
import com.eghm.configuration.interceptor.InterceptorAdapter;
import com.eghm.constant.CommonConstant;
import com.eghm.constants.ConfigConstant;
import com.eghm.dto.ext.ApiHolder;
import com.eghm.dto.ext.RequestMessage;
import com.eghm.enums.ErrorCode;
import com.eghm.exception.ParameterException;
import com.eghm.service.sys.impl.SysConfigApi;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 签名验证(建议https+签名)
 * @author 殿小二
 * @date 2020/7/23
 */
@Slf4j
@AllArgsConstructor
public class SignatureInterceptor implements InterceptorAdapter {

    private final SysConfigApi sysConfigApi;

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) {
        RequestMessage message = ApiHolder.get();
        if (StrUtil.isBlank(message.getTimestamp())) {
            throw new ParameterException(ErrorCode.SIGNATURE_TIMESTAMP_NULL);
        }
        long clientTimestamp = Long.parseLong(message.getTimestamp());
        long systemTimestamp = System.currentTimeMillis();
        long deviation = sysConfigApi.getLong(ConfigConstant.TIMESTAMP_DEVIATION);
        // 服务端时间误差
        if (Math.abs(systemTimestamp - clientTimestamp) > deviation) {
            log.warn("客户端服务端时间误差:[{}] ms", Math.abs(systemTimestamp - clientTimestamp));
            throw new ParameterException(ErrorCode.SIGNATURE_TIMESTAMP_ERROR);
        }
        if (StrUtil.isNotBlank(message.getRequestBody())) {
            String digestHex = MD5.create().digestHex(message.getTimestamp() + Base64.decodeStr(message.getRequestBody(), CommonConstant.CHARSET));
            if (!digestHex.equals(message.getSignature())) {
                log.warn("签名信息校验失败 signature:[{}], digestHex:[{}]", message.getSignature(), digestHex);
                throw new ParameterException(ErrorCode.SIGNATURE_VERIFY_ERROR);
            }
        }
        return true;
    }
}
