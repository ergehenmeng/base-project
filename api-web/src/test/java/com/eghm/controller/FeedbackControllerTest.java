package com.eghm.controller;

import com.eghm.BaseTest;
import org.junit.Before;
import org.junit.Test;

public class FeedbackControllerTest extends BaseTest {

    @Before
    public void before() {
        super.before();
        super.loginByAccount("13136113694", "111111");
    }

    @Test
    public void feedback() {
        params.put("classify", "1");
        params.put("content", "你吃了没");
        super.postSecret("/api/user/feedback");
    }
}