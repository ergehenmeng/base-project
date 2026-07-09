package com.eghm.infrastructure.integration.common.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.eghm.common.IdGenerator;
import org.springframework.stereotype.Component;

/**
 * MyBatis-backed id generator adapter.
 */
@Component
public class MybatisIdGenerator implements IdGenerator {

    @Override
    public Long nextId() {
        return IdWorker.getId();
    }
}
