package com.fanyin.service.cache;

import com.fanyin.BaseTest;
import com.fanyin.model.ext.AsyncResponse;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

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