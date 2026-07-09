package com.eghm.interfaces.webapp.configuration.interceptor;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.HMac;
import com.eghm.cache.CacheProxyService;
import com.eghm.configuration.interceptor.InterceptorAdapter;
import com.eghm.configuration.authentication.ApiHolder;
import com.eghm.constants.CommonConstant;
import com.eghm.dto.ext.RequestMessage;
import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import com.eghm.utils.WebUtil;
import com.eghm.vo.operate.auth.AuthConfigVO;
import com.eghm.interfaces.webapp.annotation.ApiSign;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

import static com.eghm.utils.StringUtil.isBlank;

/**
 *  原签名方式为 MD5 + RSA, 但由于MD5太过于简单, RSA需要把私钥给第三方,不符合RSA规范, 又不想让第三方提供公钥, 因此采用HMAC-SHA256签名
 *
 * @author 二哥很猛
 * @since 2023/10/20
 */
@Slf4j
@AllArgsConstructor
public class ApiSignInterceptor implements InterceptorAdapter {

    private final CacheProxyService cacheProxyService;

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
        long timestamp = Long.parseLong(message.getTimestamp());
        long interval = Math.abs(System.currentTimeMillis() - timestamp);
        if (interval > CommonConstant.MAX_SYSTEM_TIME_DIFF) {
            log.warn("签名信息已过期 [{}] [{}]", timestamp, interval);
            WebUtil.printJson(response, ErrorCode.SIGNATURE_EXPIRE);
            return false;
        }
        AuthConfigVO config = cacheProxyService.getByAppId(message.getAppId());
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
        rsaSignVerify(config.getAppId(), config.getAppSecret(), message.getTimestamp(), message.getNonce(), message.getRequestParam(), message.getSignature());
        return true;
    }

    /**
     * 校验签名信息
     *
     * @param appSecret  秘钥
     * @param requestBody 请求参数
     * @param timestamp   时间戳
     * @param nonce       随机数
     * @param signature   签名信息
     */
    private static void rsaSignVerify(String appId, String appSecret, String requestBody, String timestamp, String nonce, String signature) {
        String strToSign = appId + timestamp + nonce + Base64.encode(requestBody.getBytes(StandardCharsets.UTF_8));
        HMac mac = SecureUtil.hmacSha256(appSecret.getBytes(StandardCharsets.UTF_8));
        String hex = mac.digestHex(strToSign);
        if (!hex.equals(signature)) {
            log.warn("签名信息验证失败 [{}] [{}] [{}] [{}] [{}] [{}]", appId, appSecret, timestamp, nonce, requestBody, signature);
            throw new BusinessException(ErrorCode.SIGNATURE_VERIFY_ERROR);
        }
    }
}
