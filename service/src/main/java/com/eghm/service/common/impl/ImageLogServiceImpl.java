package com.eghm.service.common.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.image.ImageAddRequest;
import com.eghm.dto.image.ImageEditRequest;
import com.eghm.dto.image.ImageQueryRequest;
import com.eghm.mapper.ImageLogMapper;
import com.eghm.model.ImageLog;
import com.eghm.service.common.ImageLogService;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @since 2018/11/27 17:11
 */
@Service("imageLogService")
@AllArgsConstructor
public class ImageLogServiceImpl implements ImageLogService {

    private final ImageLogMapper imageLogMapper;

    @Override
    public Page<ImageLog> getByPage(ImageQueryRequest request) {
        LambdaQueryWrapper<ImageLog> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(request.getClassify() != null, ImageLog::getClassify, request.getClassify());
        wrapper.and(StrUtil.isNotBlank(request.getQueryName()), queryWrapper ->
                queryWrapper.like(ImageLog::getTitle, request.getQueryName()).or()
                        .like(ImageLog::getRemark, request.getQueryName()));
        wrapper.orderByDesc(ImageLog::getId);
        return imageLogMapper.selectPage(request.createPage(), wrapper);
    }

    @Override
    public void create(ImageAddRequest request) {
        ImageLog imageLog = DataUtil.copy(request, ImageLog.class);
        imageLogMapper.insert(imageLog);
    }

    @Override
    public void delete(Long id) {
        imageLogMapper.deleteById(id);
    }

    @Override
    public void update(ImageEditRequest request) {
        ImageLog log = DataUtil.copy(request, ImageLog.class);
        imageLogMapper.updateById(log);
    }

    @Override
    public ImageLog getById(Long id) {
        return imageLogMapper.selectById(id);
    }
}
