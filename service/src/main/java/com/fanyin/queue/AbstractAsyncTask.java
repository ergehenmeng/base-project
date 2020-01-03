package com.fanyin.queue;

import com.fanyin.common.enums.ErrorCode;
import com.fanyin.common.exception.BusinessException;
import com.fanyin.model.ext.AsyncKey;
import com.fanyin.model.ext.AsyncResponse;
import com.fanyin.service.cache.CacheService;
import com.fanyin.utils.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 二哥很猛
 * @date 2018/12/21 16:42
 */
@Slf4j
public abstract class AbstractAsyncTask<T extends AsyncKey, B> extends AbstractTask<T, B> {

    public AbstractAsyncTask(T data, B bean) {
        super(data, bean);
    }

    @Override
    protected void doException(Exception e) {
        super.doException(e);
        AsyncResponse asyncResponse = new AsyncResponse();
        if (e instanceof BusinessException) {
            BusinessException exception = (BusinessException) e;
            asyncResponse.setCode(exception.getCode());
            asyncResponse.setMsg(exception.getMessage());
        } else {
            ErrorCode unknownError = getUnknownError();
            asyncResponse.setCode(unknownError.getCode());
            asyncResponse.setMsg(unknownError.getMsg());
        }
        asyncResponse.setKey(getData().getKey());
        CacheService cacheService = (CacheService) SpringContextUtil.getBean("cacheService");
        cacheService.cacheAsyncResponse(asyncResponse);
    }

    /**
     * 在队列执行时如果出现未知的异常信息时应该显示给前台的错误信息 默认系统异常
     *
     * @return 异常信息枚举(用于显示给前台)
     */
    protected ErrorCode getUnknownError() {
        return ErrorCode.SYSTEM_ERROR;
    }

}
