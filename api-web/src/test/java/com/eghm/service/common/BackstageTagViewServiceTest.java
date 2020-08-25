package com.eghm.service.common;

import com.eghm.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class BackstageTagViewServiceTest extends BaseTest {

    @Autowired
    private TagViewService tagViewService;

    @Test
    public void getList() {
        tagViewService.getList();
    }
}