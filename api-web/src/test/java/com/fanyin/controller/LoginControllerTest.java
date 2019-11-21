package com.fanyin.controller;

import com.fanyin.BaseTest;
import org.junit.Test;

import static org.junit.Assert.*;

public class LoginControllerTest extends BaseTest {

    @Test
    public void byAccount() {
        params.put("account","13136113694");
        params.put("pwd","ab7792a82e6ed7c0ab12ab322e0f1171");
        super.post("/api/login/by_account");
    }
}