package com.fanyin.interceptor;

import com.fanyin.common.constant.CommonConstant;
import com.fanyin.common.constant.AppHeader;
import com.fanyin.common.enums.ErrorCode;
import com.fanyin.common.exception.RequestException;
import com.fanyin.common.utils.SignatureUtil;
import com.fanyin.constants.ConfigConstant;
import com.fanyin.constants.SystemConstant;
import com.fanyin.service.system.impl.SystemConfigApi;
import com.google.common.io.BaseEncoding;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 签名验证 sha256Hmac(appKey + Base64(json) + timestamp)
 * @author 二哥很猛
 * @date 2019/7/4 14:23
 */
public class SignatureHandlerInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private SystemConfigApi systemConfigApi;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String signature = request.getHeader(AppHeader.SIGNATURE);
        if(signature == null){
            throw new RequestException(ErrorCode.SIGNATURE_ERROR);
        }
        String timestamp = request.getHeader(AppHeader.TIMESTAMP);

        if(timestamp == null){
            throw new RequestException(ErrorCode.SIGNATURE_TIMESTAMP_NULL);
        }
        //客户端与服务端时间戳对比
        int timestampDeviation = systemConfigApi.getInt(ConfigConstant.TIMESTAMP_DEVIATION);
        long clientTimestamp = Long.parseLong(timestamp);
        if(Math.abs(System.currentTimeMillis() - clientTimestamp) > timestampDeviation){
            throw new RequestException(ErrorCode.SIGNATURE_TIMESTAMP_NULL);
        }
        String json = this.getRequestJson(request);
        String sign = SignatureUtil.sign(BaseEncoding.base64().encode(json.getBytes(SystemConstant.CHARSET)) + timestamp);
        if(!signature.equals(sign)){
            throw new RequestException(ErrorCode.SIGNATURE_VERIFY_ERROR);
        }
        return true;
    }


    /**
     * 获取请求中的json串
     * @param request request
     * @return {"a":b}
     */
    private String getRequestJson(HttpServletRequest request){
        try {
            return IOUtils.toString(request.getInputStream(), CommonConstant.CHARSET);
        }catch (Exception e){
            throw new RequestException(ErrorCode.REQUEST_RESOLVE_ERROR);
        }
    }
}
