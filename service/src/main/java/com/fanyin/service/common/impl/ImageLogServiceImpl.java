package com.fanyin.service.common.impl;

import com.fanyin.dao.mapper.common.ImageLogMapper;
import com.fanyin.dao.model.business.ImageLog;
import com.fanyin.model.dto.business.image.ImageAddRequest;
import com.fanyin.model.dto.business.image.ImageEditRequest;
import com.fanyin.model.dto.business.image.ImageQueryRequest;
import com.fanyin.service.common.ImageLogService;
import com.fanyin.utils.DataUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2018/11/27 17:11
 */
@Service("imageLogService")
@Transactional(rollbackFor = RuntimeException.class)
public class ImageLogServiceImpl implements ImageLogService {

    @Autowired
    private ImageLogMapper imageLogMapper;

    @Override
    public PageInfo<ImageLog> getByPage(ImageQueryRequest request) {
        PageHelper.startPage(request.getPage(), request.getPageSize());
        List<ImageLog> list = imageLogMapper.getList(request);
        return new PageInfo<>(list);
    }

    @Override
    public void addImageLog(ImageAddRequest request) {
        ImageLog imageLog = DataUtil.copy(request, ImageLog.class);
        imageLog.setDeleted(false);
        imageLogMapper.insertSelective(imageLog);
    }

    @Override
    public void deleteImageLog(Integer id) {
        ImageLog log = new ImageLog();
        log.setId(id);
        log.setDeleted(true);
        imageLogMapper.updateByPrimaryKeySelective(log);
    }

    @Override
    public void updateImageLog(ImageEditRequest request) {
        ImageLog log = DataUtil.copy(request, ImageLog.class);
        imageLogMapper.updateByPrimaryKeySelective(log);
    }

    @Override
    public ImageLog getById(Integer id) {
        return imageLogMapper.selectByPrimaryKey(id);
    }
}
