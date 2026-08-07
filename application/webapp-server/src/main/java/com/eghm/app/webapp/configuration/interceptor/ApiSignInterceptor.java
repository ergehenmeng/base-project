package com.eghm.app.webapp.configuration.interceptor;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.HMac;
import com.eghm.app.webapp.annotation.ApiSign;
import com.eghm.foundation.core.configuration.authentication.ApiHolder;
import com.eghm.foundation.core.constants.CommonConstant;
import com.eghm.foundation.core.dto.ext.RequestMessage;
import com.eghm.foundation.core.enums.ErrorCode;
import com.eghm.foundation.core.exception.BusinessException;
import com.eghm.foundation.web.config.interceptor.InterceptorAdapter;
import com.eghm.foundation.web.utility.WebUtil;
import com.eghm.platform.iam.service.AuthConfigCacheService;
import com.eghm.platform.iam.vo.AuthConfigVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

import static com.eghm.foundation.core.utils.StringUtil.isBlank;

/**
 * 第三方接口使用HMAC-SHA256进行请求签名校验。
 *
 * @author 二哥很猛
 * @since 2023/10/20
 */
@Slf4j
@AllArgsConstructor
public class ApiSignInterceptor implements InterceptorAdapter {

    private final AuthConfigCacheService authConfigCacheService;

    @Override
    public boolean beforeHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        ApiSign check = this.getAnnotation(handler, ApiSign.class);
        if (check == null) {
            return true;
        }
        RequestMessage message = ApiHolder.get();
        if (isBlank(message.getAppId()) || isBlank(message.getNonce()) || isBlank(message.getSignature()) || isBlank(message.getTimestamp())) {
            log.warn("请求头签名信息不全 [{}] [{}] [{}] [{}]", message.getAppId(), message.getNonce(), message.getSignature(), message.getTimestamp());
            WebUtil.printJson(response, ErrorCode.SIGNATURE_ERROR);
            return false;
        }
        long timestamp;
        try {
            timestamp = Long.parseLong(message.getTimestamp());
        } catch (NumberFormatException e) {
            log.warn("签名时间戳格式非法 [{}]", message.getAppId());
            WebUtil.printJson(response, ErrorCode.SIGNATURE_ERROR);
            return false;
        }
        if (Math.abs(System.currentTimeMillis() - timestamp) > CommonConstant.MAX_SYSTEM_TIME_DIFF) {
            log.warn("签名信息已过期 [{}]", timestamp);
            WebUtil.printJson(response, ErrorCode.SIGNATURE_EXPIRE);
            return false;
        }
        AuthConfigVO config = authConfigCacheService.getByAppId(message.getAppId());
        if (config == null) {
            log.warn("本地未查询到指定的签名信息 [{}]", message.getAppId());
            WebUtil.printJson(response, ErrorCode.SIGNATURE_VERIFY_ERROR);
            return false;
        }
        if (config.getExpireDate() == null || LocalDate.now().isAfter(config.getExpireDate())) {
            log.warn("签名信息已过有效期, 需要重新申请 [{}] [{}]", message.getAppId(), config.getExpireDate());
            WebUtil.printJson(response, ErrorCode.SIGNATURE_TIMESTAMP_ERROR);
            return false;
        }
        verifySignature(config.getAppId(), config.getAppSecret(), message.getTimestamp(), message.getNonce(), message.getRequestParam(), message.getSignature());
        return true;
    }

    /**
     * 校验签名信息
     *
     * @param appId       应用标识
     * @param appSecret   签名秘钥
     * @param timestamp   时间戳
     * @param nonce       随机数
     * @param requestParam 规范化请求参数
     * @param signature   签名信息
     */
    private static void verifySignature(String appId, String appSecret, String timestamp, String nonce, String requestParam, String signature) {
        if (requestParam == null) {
            throw new BusinessException(ErrorCode.SIGNATURE_VERIFY_ERROR);
        }
        String expectedSignature = generateSignature(appId, appSecret, timestamp, nonce, requestParam);
        if (!expectedSignature.equals(signature)) {
            log.warn("签名信息验证失败 [{}] [{}] [{}]", appId, timestamp, nonce);
            throw new BusinessException(ErrorCode.SIGNATURE_VERIFY_ERROR);
        }
    }

    /**
     * 生成签名, 供验签逻辑和测试向量复用。
     */
    static String generateSignature(String appId, String appSecret, String timestamp, String nonce, String requestParam) {
        String data = Base64.encode(requestParam.getBytes(StandardCharsets.UTF_8));
        String strToSign = appId + timestamp + nonce + data;
        HMac mac = SecureUtil.hmacSha256(appSecret.getBytes(StandardCharsets.UTF_8));
        return mac.digestHex(strToSign, StandardCharsets.UTF_8);
    }
}
