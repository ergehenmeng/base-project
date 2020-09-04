package com.eghm.service.common.impl;

import com.eghm.common.enums.ErrorCode;
import com.eghm.common.exception.BusinessException;
import com.eghm.dao.mapper.business.TaskConfigMapper;
import com.eghm.dao.model.business.TaskConfig;
import com.eghm.model.dto.task.TaskEditRequest;
import com.eghm.model.dto.task.TaskQueryRequest;
import com.eghm.service.common.TaskConfigService;
import com.eghm.utils.DataUtil;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.support.CronSequenceGenerator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2019/9/6 15:19
 */
@Service("taskConfigService")
@Transactional(rollbackFor = RuntimeException.class)
public class TaskConfigServiceImpl implements TaskConfigService {

    private TaskConfigMapper taskConfigMapper;

    @Autowired
    public void setTaskConfigMapper(TaskConfigMapper taskConfigMapper) {
        this.taskConfigMapper = taskConfigMapper;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class, readOnly = true)
    public List<TaskConfig> getAvailableList() {
        return taskConfigMapper.getAvailableList();
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class, readOnly = true)
    public PageInfo<TaskConfig> getByPage(TaskQueryRequest request) {
        PageMethod.startPage(request.getPage(), request.getPageSize());
        List<TaskConfig> list = taskConfigMapper.getList(request);
        return new PageInfo<>(list);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class, readOnly = true)
    public TaskConfig getById(Integer id) {
        return taskConfigMapper.selectByPrimaryKey(id);
    }

    @Override
    public void editTaskConfig(TaskEditRequest request) {
        if (!CronSequenceGenerator.isValidExpression(request.getCronExpression())) {
            throw new BusinessException(ErrorCode.CRON_CONFIG_ERROR);
        }
        TaskConfig config = DataUtil.copy(request, TaskConfig.class);
        taskConfigMapper.updateByPrimaryKeySelective(config);
    }
}
