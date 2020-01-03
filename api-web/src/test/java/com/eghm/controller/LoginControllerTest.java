package com.eghm.controller;

import com.eghm.BaseTest;
import org.junit.Test;

public class LoginControllerTest extends BaseTest {

    @Test
    public void byAccount() {
        params.put("account","13136113694");
        params.put("pwd","ab7792a82e6ed7c0ab12ab322e0f1171");
        super.post("/api/login/by_account");
    }
}