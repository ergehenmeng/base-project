package com.eghm.service.common.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dao.mapper.ImageLogMapper;
import com.eghm.dao.model.ImageLog;
import com.eghm.model.dto.image.ImageAddRequest;
import com.eghm.model.dto.image.ImageEditRequest;
import com.eghm.model.dto.image.ImageQueryRequest;
import com.eghm.service.common.ImageLogService;
import com.eghm.utils.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 二哥很猛
 * @date 2018/11/27 17:11
 */
@Service("imageLogService")
public class ImageLogServiceImpl implements ImageLogService {

    private ImageLogMapper imageLogMapper;

    @Autowired
    public void setImageLogMapper(ImageLogMapper imageLogMapper) {
        this.imageLogMapper = imageLogMapper;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class, readOnly = true)
    public Page<ImageLog> getByPage(ImageQueryRequest request) {
        LambdaQueryWrapper<ImageLog> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(ImageLog::getDeleted, false);
        wrapper.eq(request.getClassify() != null, ImageLog::getClassify, request.getClassify());
        wrapper.and(StrUtil.isNotBlank(request.getQueryName()), queryWrapper ->
                queryWrapper.like(ImageLog::getTitle, request.getQueryName()).or()
                        .like(ImageLog::getRemark, request.getQueryName()));
        wrapper.orderByDesc(ImageLog::getId);
        return imageLogMapper.selectPage(request.createPage(), wrapper);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void addImageLog(ImageAddRequest request) {
        ImageLog imageLog = DataUtil.copy(request, ImageLog.class);
        imageLog.setDeleted(false);
        imageLogMapper.insert(imageLog);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void deleteImageLog(Long id) {
        ImageLog log = new ImageLog();
        log.setId(id);
        log.setDeleted(true);
        imageLogMapper.updateById(log);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void updateImageLog(ImageEditRequest request) {
        ImageLog log = DataUtil.copy(request, ImageLog.class);
        imageLogMapper.updateById(log);
    }

    @Override
    public ImageLog getById(Long id) {
        return imageLogMapper.selectById(id);
    }
}
