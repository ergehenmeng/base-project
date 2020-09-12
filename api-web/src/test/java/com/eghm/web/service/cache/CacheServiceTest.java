package com.eghm.web.service.cache;

import com.eghm.model.ext.AsyncResponse;
import com.eghm.service.cache.CacheService;
import com.eghm.web.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class CacheServiceTest extends BaseTest {

    @Autowired
    private CacheService cacheService;

    @Test
    public void cacheAsyncResponse() {
        AsyncResponse response = new AsyncResponse();
        response.setCode(1);
        response.setKey("key");
        response.setMsg("msg2");
        cacheService.cacheAsyncResponse(response);
    }
}