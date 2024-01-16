package com.eghm.service.sys.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.log.ManageQueryRequest;
import com.eghm.mapper.ManageLogMapper;
import com.eghm.model.ManageLog;
import com.eghm.service.sys.ManageLogService;
import com.eghm.vo.log.ManageLogResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 操作日期
 *
 * @author 二哥很猛
 * @date 2019/1/15 17:55
 */
@Service("manageLogService")
@AllArgsConstructor
public class ManageLogServiceImpl implements ManageLogService {

    private final ManageLogMapper manageLogMapper;

    @Override
    public Page<ManageLogResponse> getByPage(ManageQueryRequest request) {
        return manageLogMapper.getByPage(request.createPage(), request);
    }

    @Override
    public void insertManageLog(ManageLog log) {
        manageLogMapper.insert(log);
    }

    @Override
    public String getResponseById(Long id) {
        return manageLogMapper.getResponseById(id);
    }
}
