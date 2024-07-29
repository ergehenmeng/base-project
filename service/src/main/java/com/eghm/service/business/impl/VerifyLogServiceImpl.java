package com.eghm.service.business.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.verify.VerifyLogQueryRequest;
import com.eghm.mapper.VerifyLogMapper;
import com.eghm.model.VerifyLog;
import com.eghm.service.business.VerifyLogService;
import com.eghm.vo.business.verify.VerifyLogResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2022/8/6
 */
@Service("verifyLogService")
@AllArgsConstructor
@Slf4j
public class VerifyLogServiceImpl implements VerifyLogService {

    private final VerifyLogMapper verifyLogMapper;

    @Override
    public Page<VerifyLogResponse> getByPage(VerifyLogQueryRequest request) {
        return verifyLogMapper.getByPage(request.createPage(), request);
    }

    @Override
    public List<VerifyLogResponse> getList(VerifyLogQueryRequest request) {
        return verifyLogMapper.getByPage(request.createNullPage(), request).getRecords();
    }

    @Override
    public int getVerifiedNum(String orderNo) {
        return verifyLogMapper.getVerifiedNum(orderNo);
    }

    @Override
    public void insert(VerifyLog verifyLog) {
        verifyLogMapper.insert(verifyLog);
    }
}
