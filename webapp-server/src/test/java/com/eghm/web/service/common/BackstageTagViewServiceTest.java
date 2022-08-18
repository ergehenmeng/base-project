package com.eghm.web.service.common;

import com.eghm.service.common.TagViewService;
import com.eghm.web.BaseTest;
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