package com.eghm.integration.storage.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.integration.storage.dto.ImageAddRequest;
import com.eghm.integration.storage.dto.ImageEditRequest;
import com.eghm.integration.storage.dto.ImageQueryRequest;
import com.eghm.integration.storage.mapper.ImageLogMapper;
import com.eghm.integration.storage.entity.ImageLog;
import com.eghm.integration.storage.service.ImageLogService;
import com.eghm.foundation.web.utility.DataUtil;
import com.eghm.integration.storage.vo.ImageLogResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @since 2018/11/27 17:11
 */
@Service
@AllArgsConstructor
public class ImageLogServiceImpl implements ImageLogService {

    private final ImageLogMapper imageLogMapper;

    @Override
    public Page<ImageLogResponse> getByPage(ImageQueryRequest request) {
        return imageLogMapper.getByPage(request.createPage(), request);
    }

    @Override
    public void create(ImageAddRequest request) {
        DataUtil.copy(request, ImageLog.class, imageLogMapper::insert);
    }

    @Override
    public void delete(Long id) {
        imageLogMapper.deleteById(id);
    }

    @Override
    public void update(ImageEditRequest request) {
        DataUtil.copy(request, ImageLog.class, imageLogMapper::updateById);
    }

}
