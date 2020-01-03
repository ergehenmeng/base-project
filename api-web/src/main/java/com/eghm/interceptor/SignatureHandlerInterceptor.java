package com.eghm.interceptor;

import com.eghm.common.constant.AppHeader;
import com.eghm.common.constant.CommonConstant;
import com.eghm.common.enums.ErrorCode;
import com.eghm.common.exception.RequestException;
import com.eghm.common.utils.Md5Util;
import com.eghm.constants.ConfigConstant;
import com.eghm.constants.SystemConstant;
import com.eghm.model.ext.RequestMessage;
import com.eghm.model.ext.RequestThreadLocal;
import com.eghm.service.system.impl.SystemConfigApi;
import com.google.common.io.BaseEncoding;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 签名验证 md5(signKey + Base64(json) + timestamp) 仅在登陆后才有效
 *
 * @author 二哥很猛
 * @date 2019/7/4 14:23
 */
public class SignatureHandlerInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private SystemConfigApi systemConfigApi;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (systemConfigApi.getBoolean(ConfigConstant.SIGNATURE_VERIFICATION)) {
            String signature = request.getHeader(AppHeader.SIGNATURE);
            if (signature == null) {
                throw new RequestException(ErrorCode.SIGNATURE_ERROR);
            }
            String timestamp = request.getHeader(AppHeader.TIMESTAMP);
            if (timestamp == null) {
                throw new RequestException(ErrorCode.SIGNATURE_TIMESTAMP_NULL);
            }
            int timestampDeviation = systemConfigApi.getInt(ConfigConstant.TIMESTAMP_DEVIATION);
            long clientTimestamp = Long.parseLong(timestamp);
            //客户端与服务端时间戳对比
            if (Math.abs(System.currentTimeMillis() - clientTimestamp) > timestampDeviation) {
                throw new RequestException(ErrorCode.SIGNATURE_TIMESTAMP_ERROR);
            }
            RequestMessage message = RequestThreadLocal.get();
            String requestBody = this.getRequestBody(request);
            //签名处理
            String sign = Md5Util.md5(message.getSignKey() + BaseEncoding.base64().encode(requestBody.getBytes(SystemConstant.CHARSET)) + timestamp);
            if (!signature.equals(sign)) {
                throw new RequestException(ErrorCode.SIGNATURE_VERIFY_ERROR);
            }
            //解析后放入缓存方便后面程序使用
            message.setRequestBody(requestBody);
        }
        return true;
    }


    /**
     * 获取请求中的json串
     *
     * @param request request
     * @return {"a":b}
     */
    private String getRequestBody(HttpServletRequest request) {
        try {
            return IOUtils.toString(request.getInputStream(), CommonConstant.CHARSET);
        } catch (Exception e) {
            throw new RequestException(ErrorCode.REQUEST_RESOLVE_ERROR);
        }
    }
}
