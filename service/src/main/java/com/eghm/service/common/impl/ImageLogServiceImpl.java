package com.eghm.service.common.impl;

import com.eghm.dao.mapper.ImageLogMapper;
import com.eghm.dao.model.ImageLog;
import com.eghm.model.dto.image.ImageAddRequest;
import com.eghm.model.dto.image.ImageEditRequest;
import com.eghm.model.dto.image.ImageQueryRequest;
import com.eghm.service.common.ImageLogService;
import com.eghm.utils.DataUtil;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public PageInfo<ImageLog> getByPage(ImageQueryRequest request) {
        PageMethod.startPage(request.getPage(), request.getPageSize());
        List<ImageLog> list = imageLogMapper.getList(request);
        return new PageInfo<>(list);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void addImageLog(ImageAddRequest request) {
        ImageLog imageLog = DataUtil.copy(request, ImageLog.class);
        imageLog.setDeleted(false);
        imageLogMapper.insertSelective(imageLog);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void deleteImageLog(Long id) {
        ImageLog log = new ImageLog();
        log.setId(id);
        log.setDeleted(true);
        imageLogMapper.updateByPrimaryKeySelective(log);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void updateImageLog(ImageEditRequest request) {
        ImageLog log = DataUtil.copy(request, ImageLog.class);
        imageLogMapper.updateByPrimaryKeySelective(log);
    }

    @Override
    public ImageLog getById(Long id) {
        return imageLogMapper.selectByPrimaryKey(id);
    }
}
