package com.eghm.service.operate.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.image.ImageAddRequest;
import com.eghm.dto.image.ImageEditRequest;
import com.eghm.dto.image.ImageQueryRequest;
import com.eghm.mapper.ImageLogMapper;
import com.eghm.model.ImageLog;
import com.eghm.service.operate.ImageLogService;
import com.eghm.utils.DataUtil;
import com.eghm.vo.log.ImageLogResponse;
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
    public Page<ImageLogResponse> getByPage(ImageQueryRequest request) {
        return imageLogMapper.getByPage(request.createPage(), request);
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

}
