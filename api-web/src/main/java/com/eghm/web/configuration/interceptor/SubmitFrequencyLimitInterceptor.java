package com.eghm.web.configuration.interceptor;

import com.eghm.common.constant.CacheConstant;
import com.eghm.common.enums.ErrorCode;
import com.eghm.common.exception.BusinessException;
import com.eghm.constants.ConfigConstant;
import com.eghm.model.dto.ext.ApiHolder;
import com.eghm.service.cache.CacheService;
import com.eghm.service.sys.impl.SysConfigApi;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 殿小二
 * @date 2020/12/15
 */
public class SubmitFrequencyLimitInterceptor implements InterceptorAdapter{

    @Autowired
    private SysConfigApi sysConfigApi;

    @Autowired
    private CacheService cacheService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!this.supportHandler(handler)) {
            return true;
        }
        Integer userId = ApiHolder.getUserId();
        String uri = request.getRequestURI();
        String key = String.format(CacheConstant.SUBMIT_LIMIT, userId, uri);
        if (cacheService.exist(key)) {
            throw new BusinessException(ErrorCode.SUBMIT_FREQUENTLY);
        }
        long submitLimit = sysConfigApi.getLong(ConfigConstant.SUBMIT_FREQUENCY_LIMIT);
        cacheService.setValue(key, true, submitLimit);
        return true;
    }
}
