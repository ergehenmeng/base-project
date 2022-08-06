package com.eghm.service.business.impl;

import com.eghm.dao.mapper.VerifyLogMapper;
import com.eghm.service.business.VerifyLogService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @date 2022/8/6
 */
@Service("verifyLogService")
@AllArgsConstructor
@Slf4j
public class VerifyLogServiceImpl implements VerifyLogService {

    private final VerifyLogMapper verifyLogMapper;

    @Override
    public int getVerifiedNum(Long orderId) {
        return verifyLogMapper.getVerifiedNum(orderId);
    }
}
