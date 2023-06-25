package com.eghm.web.configuration.interceptor;

import com.eghm.configuration.interceptor.InterceptorAdapter;
import com.eghm.constant.AppHeader;
import com.eghm.constant.CommonConstant;
import com.eghm.dto.ext.ApiHolder;
import com.eghm.dto.ext.RequestMessage;
import com.eghm.enums.ErrorCode;
import com.eghm.exception.ParameterException;
import com.eghm.utils.WebUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.lang.NonNull;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 基础头信息收集 将定义好的头信息参数全部放入RequestMessage对象中
 *
 * @author 二哥很猛
 * @date 2019/7/4 14:24
 */
@Slf4j
public class MessageInterceptor implements InterceptorAdapter {

    /**
     * 请求头最大长度 默认256
     */
    private static final int MAX_HEADER_LENGTH = 256;

    @Override
    public boolean beforeHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) throws IOException {
        //app请求头信息
        String channel = request.getHeader(AppHeader.CHANNEL);
        String version = request.getHeader(AppHeader.VERSION);
        String osVersion = request.getHeader(AppHeader.OS_VERSION);
        String deviceBrand = request.getHeader(AppHeader.DEVICE_BRAND);
        String deviceModel = request.getHeader(AppHeader.DEVICE_MODEL);
        String signature = request.getHeader(AppHeader.SIGNATURE);
        String timestamp = request.getHeader(AppHeader.TIMESTAMP);
        String serialNumber = request.getHeader(AppHeader.SERIAL_NUMBER);
        if (checkHeaderLength(channel)
                || checkHeaderLength(version)
                || checkHeaderLength(osVersion)
                || checkHeaderLength(deviceBrand)
                || checkHeaderLength(deviceModel)
                || checkHeaderLength(signature)
                || checkHeaderLength(timestamp)
                || checkHeaderLength(serialNumber)) {
            // 该信息会保存在Thread中,会占用一定内存,防止恶意攻击做此判断
            WebUtil.printJson(response, ErrorCode.REQUEST_PARAM_ILLEGAL);
            return false;
        }
        RequestMessage message = ApiHolder.get();
        message.setVersion(version);
        message.setChannel(channel);
        message.setOsVersion(osVersion);
        message.setDeviceBrand(deviceBrand);
        message.setDeviceModel(deviceModel);
        message.setTimestamp(timestamp);
        message.setSignature(signature);
        message.setSerialNumber(serialNumber);
        // 需要将requestBody中的数据采集,方便做日志
        try {
            String requestBody = IOUtils.toString(request.getInputStream(), CommonConstant.CHARSET);
            message.setRequestBody(requestBody);
        } catch (IOException e) {
            log.warn("获取请求参数信息异常", e);
            throw new ParameterException(ErrorCode.READ_PARAM_ERROR);
        }
        return true;
    }


    @Override
    public void afterCompletion(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler, Exception ex) {
        ApiHolder.remove();
    }

    /**
     * 检查header请求参数是否过长,防止恶意攻击导致服务器挂掉,最大长度不能超过256
     *
     * @param headerValue 字符串
     * @return true不合法 false合法
     */
    private boolean checkHeaderLength(String headerValue) {
        return headerValue != null && headerValue.length() > MAX_HEADER_LENGTH;
    }

}
