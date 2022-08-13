package com.eghm.service.sys.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dao.mapper.ManageLogMapper;
import com.eghm.dao.model.ManageLog;
import com.eghm.model.dto.log.ManageQueryRequest;
import com.eghm.service.sys.ManageLogService;
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
    public void insertManageLog(ManageLog log) {
        manageLogMapper.insert(log);
    }

    @Override
    public Page<ManageLog> getByPage(ManageQueryRequest request) {
        return manageLogMapper.getByPage(request.createPage(), request);
    }

    @Override
    public String getResponseById(Long id) {
        return manageLogMapper.getResponseById(id);
    }
}
