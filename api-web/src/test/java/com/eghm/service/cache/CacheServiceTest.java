package com.eghm.service.cache;

import com.eghm.BaseTest;
import com.eghm.model.ext.AsyncResponse;
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