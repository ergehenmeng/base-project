package com.eghm.web.controller;

import com.eghm.common.utils.HttpClientUtil;
import com.eghm.web.BaseTest;
import org.junit.Test;

public class LoginControllerTest extends BaseTest {

    @Test
    public void byAccount() {
        params.put("account","13136113694");
        params.put("pwd","96e79218965eb72c92a549dd5a330112");
        super.post("/api/login/account");
    }


    @Test
    public void test2(){
        System.out.println(HttpClientUtil.get("https://www.baidu.com/"));
    }
}