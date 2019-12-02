package com.fanyin.service.common;

import com.fanyin.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class TagViewServiceTest extends BaseTest {

    @Autowired
    private TagViewService tagViewService;

    @Test
    public void getList() {
        tagViewService.getList();
    }
}