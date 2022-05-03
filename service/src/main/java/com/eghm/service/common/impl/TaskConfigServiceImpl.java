package com.eghm.service.common.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.common.enums.ErrorCode;
import com.eghm.common.exception.BusinessException;
import com.eghm.dao.mapper.TaskConfigMapper;
import com.eghm.dao.model.TaskConfig;
import com.eghm.model.dto.task.TaskEditRequest;
import com.eghm.model.dto.task.TaskQueryRequest;
import com.eghm.service.common.TaskConfigService;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.support.CronSequenceGenerator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2019/9/6 15:19
 */
@Service("taskConfigService")
@AllArgsConstructor
public class TaskConfigServiceImpl implements TaskConfigService {

    private final TaskConfigMapper taskConfigMapper;

    @Override
    public List<TaskConfig> getAvailableList() {
        return taskConfigMapper.getAvailableList();
    }

    @Override
    public Page<TaskConfig> getByPage(TaskQueryRequest request) {
        LambdaQueryWrapper<TaskConfig> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(request.getState() != null, TaskConfig::getState, request.getState());

        wrapper.and(StrUtil.isNotBlank(request.getQueryName()), queryWrapper ->
                queryWrapper.like(TaskConfig::getTitle, request.getQueryName()).or()
                        .like(TaskConfig::getNid, request.getQueryName()).or()
                        .like(TaskConfig::getBeanName, request.getQueryName()));
        return taskConfigMapper.selectPage(request.createPage(), wrapper);
    }

    @Override
    public TaskConfig getById(Long id) {
        return taskConfigMapper.selectById(id);
    }

    @Override
    public void editTaskConfig(TaskEditRequest request) {
        if (!CronSequenceGenerator.isValidExpression(request.getCronExpression())) {
            throw new BusinessException(ErrorCode.CRON_CONFIG_ERROR);
        }
        TaskConfig config = DataUtil.copy(request, TaskConfig.class);
        taskConfigMapper.updateById(config);
    }
}
