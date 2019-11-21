package com.fanyin.interceptor;

import com.fanyin.common.constant.CommonConstant;
import com.fanyin.common.constant.AppHeader;
import com.fanyin.common.enums.ErrorCode;
import com.fanyin.common.exception.RequestException;
import com.fanyin.common.utils.Md5Util;
import com.fanyin.constants.ConfigConstant;
import com.fanyin.constants.SystemConstant;
import com.fanyin.model.ext.RequestMessage;
import com.fanyin.model.ext.RequestThreadLocal;
import com.fanyin.service.system.impl.SystemConfigApi;
import com.google.common.io.BaseEncoding;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 签名验证 md5(appKey + Base64(json) + timestamp)
 * @author 二哥很猛
 * @date 2019/7/4 14:23
 */
public class SignatureHandlerInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private SystemConfigApi systemConfigApi;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(systemConfigApi.getBoolean(ConfigConstant.SIGNATURE_VERIFICATION)){
            String signature = request.getHeader(AppHeader.SIGNATURE);
            if(signature == null){
                throw new RequestException(ErrorCode.SIGNATURE_ERROR);
            }
            String timestamp = request.getHeader(AppHeader.TIMESTAMP);
            if(timestamp == null){
                throw new RequestException(ErrorCode.SIGNATURE_TIMESTAMP_NULL);
            }
            int timestampDeviation = systemConfigApi.getInt(ConfigConstant.TIMESTAMP_DEVIATION);
            long clientTimestamp = Long.parseLong(timestamp);
            //客户端与服务端时间戳对比
            if(Math.abs(System.currentTimeMillis() - clientTimestamp) > timestampDeviation){
                throw new RequestException(ErrorCode.SIGNATURE_TIMESTAMP_ERROR);
            }
            String json = this.getRequestJson(request);
            //签名处理
            String sign = Md5Util.md5(CommonConstant.APP_KEY + BaseEncoding.base64().encode(json.getBytes(SystemConstant.CHARSET)) + timestamp);
            if(!signature.equals(sign)){
                throw new RequestException(ErrorCode.SIGNATURE_VERIFY_ERROR);
            }
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
            String json = IOUtils.toString(request.getInputStream(), CommonConstant.CHARSET);
            //解析后放入缓存方便后面程序使用
            RequestMessage message = RequestThreadLocal.get();
            message.setJsonString(json);
            return json;
        }catch (Exception e){
            throw new RequestException(ErrorCode.REQUEST_RESOLVE_ERROR);
        }
    }
}
