package com.eghm.service.common.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.SysTaskMapper;
import com.eghm.model.SysTask;
import com.eghm.dto.task.TaskEditRequest;
import com.eghm.dto.task.TaskQueryRequest;
import com.eghm.service.common.SysTaskService;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.support.CronExpression;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2019/9/6 15:19
 */
@Service("sysTaskService")
@AllArgsConstructor
public class SysTaskServiceImpl implements SysTaskService {

    private final SysTaskMapper sysTaskMapper;

    @Override
    public List<SysTask> getAvailableList() {
        LambdaQueryWrapper<SysTask> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SysTask::getState, true);
        return sysTaskMapper.selectList(wrapper);
    }

    @Override
    public Page<SysTask> getByPage(TaskQueryRequest request) {
        LambdaQueryWrapper<SysTask> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(request.getState() != null, SysTask::getState, request.getState());
        wrapper.and(StrUtil.isNotBlank(request.getQueryName()), queryWrapper ->
                queryWrapper.like(SysTask::getTitle, request.getQueryName()).or()
                        .like(SysTask::getMethodName, request.getQueryName()).or()
                        .like(SysTask::getBeanName, request.getQueryName()));
        return sysTaskMapper.selectPage(request.createPage(), wrapper);
    }

    @Override
    public SysTask getById(Long id) {
        return sysTaskMapper.selectById(id);
    }

    @Override
    public void update(TaskEditRequest request) {
        if (!CronExpression.isValidExpression(request.getCronExpression())) {
            throw new BusinessException(ErrorCode.CRON_CONFIG_ERROR);
        }
        SysTask config = DataUtil.copy(request, SysTask.class);
        sysTaskMapper.updateById(config);
    }
}
