package com.fanyin.interceptor;

import com.fanyin.common.constant.CommonConstant;
import com.fanyin.common.constant.AppHeader;
import com.fanyin.common.enums.ErrorCodeEnum;
import com.fanyin.common.exception.RequestException;
import com.fanyin.common.utils.SignatureUtil;
import com.fanyin.constants.ConfigConstant;
import com.fanyin.service.system.impl.SystemConfigApi;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 签名验证
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
            throw new RequestException(ErrorCodeEnum.SIGNATURE_ERROR);
        }
        String timestamp = request.getHeader(AppHeader.TIMESTAMP);

        if(timestamp == null){
            throw new RequestException(ErrorCodeEnum.SIGNATURE_TIMESTAMP_NULL);
        }
        //客户端与服务端时间戳对比
        int timestampDeviation = systemConfigApi.getInt(ConfigConstant.TIMESTAMP_DEVIATION);
        long clientTimestamp = Long.parseLong(timestamp);
        if(Math.abs(System.currentTimeMillis() - clientTimestamp) > timestampDeviation){
            throw new RequestException(ErrorCodeEnum.SIGNATURE_TIMESTAMP_NULL);
        }

        String json = this.getRequestJson(request);

        String sign = SignatureUtil.sign(json + "." + timestamp);
        if(!signature.equals(sign)){
            throw new RequestException(ErrorCodeEnum.SIGNATURE_VERIFY_ERROR);
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
            throw new RequestException(ErrorCodeEnum.REQUEST_RESOLVE_ERROR);
        }
    }
}
