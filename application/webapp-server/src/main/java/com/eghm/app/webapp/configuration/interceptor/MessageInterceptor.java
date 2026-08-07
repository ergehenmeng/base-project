package com.eghm.app.webapp.configuration.interceptor;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.http.Header;
import com.eghm.foundation.core.annotation.SkipLogger;
import com.eghm.foundation.core.configuration.authentication.ApiHolder;
import com.eghm.foundation.core.constants.ApplicationHeader;
import com.eghm.foundation.core.constants.CommonConstant;
import com.eghm.foundation.core.dto.ext.RequestMessage;
import com.eghm.foundation.core.enums.ErrorCode;
import com.eghm.foundation.core.exception.ParameterException;
import com.eghm.foundation.web.config.interceptor.InterceptorAdapter;
import com.eghm.foundation.web.utility.WebUtil;
import com.google.common.base.Joiner;
import jakarta.annotation.Nonnull;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

/**
 * 基础头信息收集 将定义好的头信息参数全部放入RequestMessage对象中
 *
 * @author 二哥很猛
 * @since 2019/7/4 14:24
 */
@Slf4j
public class MessageInterceptor implements InterceptorAdapter {

    /**
     * 请求头最大长度 默认128
     */
    private static final int MAX_HEADER_LENGTH = 128;

    @Override
    public boolean beforeHandle(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull Object handler) throws IOException {
        // app请求头信息
        String channel = request.getHeader(ApplicationHeader.CHANNEL);
        String version = request.getHeader(ApplicationHeader.VERSION);
        String osVersion = request.getHeader(ApplicationHeader.OS_VERSION);
        String deviceBrand = request.getHeader(ApplicationHeader.DEVICE_BRAND);
        String deviceModel = request.getHeader(ApplicationHeader.DEVICE_MODEL);
        String serialNumber = request.getHeader(ApplicationHeader.SERIAL_NUMBER);
        String appId = request.getHeader(ApplicationHeader.APP_ID);
        String nonce = request.getHeader(ApplicationHeader.NONCE);
        String signature = request.getHeader(ApplicationHeader.SIGNATURE);
        String timestamp = request.getHeader(ApplicationHeader.TIMESTAMP);
        if (checkHeaderLength(channel)
                || checkHeaderLength(version)
                || checkHeaderLength(osVersion)
                || checkHeaderLength(deviceBrand)
                || checkHeaderLength(deviceModel)
                || checkHeaderLength(serialNumber)
                || checkHeaderLength(appId)
                || checkHeaderLength(nonce)
                || checkHeaderLength(signature)
                || checkHeaderLength(timestamp)
        ) {
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
        message.setSerialNumber(serialNumber);
        message.setSignature(signature);
        message.setNonce(nonce);
        message.setAppId(appId);
        message.setTimestamp(timestamp);
        message.setRequestParam(this.parseRequestParam(request, handler));
        return true;
    }

    @Override
    public void afterCompletion(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull Object handler, Exception ex) {
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

    /**
     * 解析请求的参数信息
     *
     * @param request request
     * @param handler handler
     * @return JSON请求返回原始请求体, 其他请求返回规范化后的查询参数
     */
    private String parseRequestParam(HttpServletRequest request, Object handler) {
        // 此处增加判断是防止文件上传时解析请求数据,产生没必要内存垃圾
        SkipLogger skipLogger = this.getAnnotation(handler, SkipLogger.class);
        if (skipLogger != null) {
            return null;
        }
        // 注意: 由于request.getInputStream()方法只能读取一遍
        // 此处选择只针对json格式的post请求才会读取流信息
        String contentType = request.getHeader(Header.CONTENT_TYPE.getValue());
        if (HttpMethod.POST.matches(request.getMethod()) && contentType != null && contentType.startsWith(MediaType.APPLICATION_JSON_VALUE)) {
            try {
                return IoUtil.read(request.getInputStream(), CommonConstant.CHARSET);
            } catch (IOException e) {
                log.warn("获取POST请求参数信息异常", e);
                throw new ParameterException(ErrorCode.READ_PARAM_ERROR);
            }
        }
        return this.buildQuery(request);
    }
    
    private String buildQuery(HttpServletRequest request) {
        Map<String, String[]> map = request.getParameterMap();
        Map<String, Object> treeMap = new TreeMap<>();
        map.forEach((key, value) -> {
            if (value != null && value.length > 0) {
                treeMap.put(key, Joiner.on(',').join(value));
            } else {
                treeMap.put(key, null);
            }
        });
        return URLUtil.buildQuery(treeMap, CommonConstant.CHARSET);
    }
}
