package com.eghm.web.configuration.interceptor;

import cn.hutool.core.codec.Base64Encoder;
import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.crypto.SignUtil;
import cn.hutool.crypto.asymmetric.Sign;
import cn.hutool.crypto.asymmetric.SignAlgorithm;
import cn.hutool.crypto.digest.MD5;
import com.eghm.cache.CacheProxyService;
import com.eghm.configuration.interceptor.InterceptorAdapter;
import com.eghm.constants.CommonConstant;
import com.eghm.dto.ext.ApiHolder;
import com.eghm.dto.ext.RequestMessage;
import com.eghm.enums.ErrorCode;
import com.eghm.enums.SignType;
import com.eghm.exception.BusinessException;
import com.eghm.utils.WebUtil;
import com.eghm.vo.auth.AuthConfigVO;
import com.eghm.web.annotation.SignCheck;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Map;
import java.util.TreeMap;

import static com.eghm.utils.StringUtil.isBlank;

/**
 * @author 二哥很猛
 * @since 2023/10/20
 */
@Slf4j
@AllArgsConstructor
public class SignCheckInterceptor implements InterceptorAdapter {

    private final CacheProxyService cacheProxyService;

    @Override
    public boolean beforeHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        SignCheck check = this.getAnnotation(handler, SignCheck.class);
        if (check == null || !HttpMethod.POST.matches(request.getMethod())) {
            return true;
        }
        RequestMessage message = ApiHolder.get();
        if (isBlank(message.getAppKey()) || isBlank(message.getSignature()) || isBlank(message.getTimestamp())) {
            log.warn("请求头签名信息不全 [{}] [{}] [{}]", message.getAppKey(), message.getSignature(), message.getTimestamp());
            WebUtil.printJson(response, ErrorCode.SIGNATURE_ERROR);
            return false;
        }
        long timestamp = Long.parseLong(message.getTimestamp());
        long interval = Math.abs(System.currentTimeMillis() - timestamp);
        if (interval > CommonConstant.MAX_SIGN_EXPIRE) {
            log.warn("签名信息已过期 [{}] [{}]", timestamp, interval);
            WebUtil.printJson(response, ErrorCode.SIGNATURE_EXPIRE);
            return false;
        }
        AuthConfigVO config = cacheProxyService.getByAppKey(message.getAppKey());
        if (config == null) {
            log.warn("本地未查询到指定的签名信息 [{}]", message.getAppKey());
            WebUtil.printJson(response, ErrorCode.SIGNATURE_VERIFY_ERROR);
            return false;
        }
        if (config.getExpireDate() == null || LocalDate.now().isAfter(config.getExpireDate())) {
            log.warn("签名信息已过有效期, 需要重新申请 [{}] [{}]", message.getAppKey(), config.getExpireDate());
            WebUtil.printJson(response, ErrorCode.SIGNATURE_TIMESTAMP_ERROR);
            return false;
        }
        if (check.value() == SignType.MD5) {
            md5SignVerify(config.getPrivateKey(), message.getRequestParam(), message.getTimestamp(), message.getSignature());
        } else {
            rsaSignVerify(config.getPublicKey(), config.getPrivateKey(), message.getRequestParam(), message.getTimestamp(), message.getSignature());
        }
        return true;
    }

    /**
     * md5生成签名信息
     *
     * @param appSecret   appSecret
     * @param requestBody 请求参数
     * @param signature   源签名信息
     */
    private static void md5SignVerify(String appSecret, String requestBody, String timestamp, String signature) {
        Map<String, String> param = new TreeMap<>();
        param.put(CommonConstant.APP_SECRET, appSecret);
        param.put(CommonConstant.DATA, Base64Encoder.encode(requestBody));
        param.put(CommonConstant.TIMESTAMP, timestamp);
        String buildQuery = URLUtil.buildQuery(param, CommonConstant.CHARSET);
        String target = MD5.create().digestHex(buildQuery);
        boolean equals = signature.equals(target);
        if (!equals) {
            log.warn("md5签名信息验证失败 [{}] [{}]", signature, target);
            throw new BusinessException(ErrorCode.SIGNATURE_VERIFY_ERROR);
        }
    }

    /**
     * rsa生成签名信息 SHA256withRSA签名方式
     *
     * @param publicKey   公钥
     * @param privateKey  私钥
     * @param requestBody 请求参数
     */
    private static void rsaSignVerify(String privateKey, String publicKey, String requestBody, String timestamp, String signature) {
        Map<String, String> param = new TreeMap<>();
        param.put(CommonConstant.DATA, Base64Encoder.encode(requestBody));
        param.put(CommonConstant.TIMESTAMP, timestamp);
        String buildQuery = URLUtil.buildQuery(param, CommonConstant.CHARSET);
        Sign sign = SignUtil.sign(SignAlgorithm.SHA256withRSA, privateKey, publicKey);
        boolean verify = sign.verify(buildQuery.getBytes(StandardCharsets.UTF_8), HexUtil.decodeHex(signature));
        if (!verify) {
            log.warn("rsa签名信息验证失败 [{}] [{}] [{}] [{}]", privateKey, publicKey, requestBody, signature);
            throw new BusinessException(ErrorCode.SIGNATURE_VERIFY_ERROR);
        }
    }
}
