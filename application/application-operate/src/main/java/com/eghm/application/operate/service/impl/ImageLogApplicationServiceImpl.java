package com.eghm.application.operate.service.impl;

import com.eghm.application.shared.dto.ext.Page;
import com.eghm.application.shared.dto.operate.image.ImageAddRequest;
import com.eghm.application.shared.dto.operate.image.ImageEditRequest;
import com.eghm.application.shared.dto.operate.image.ImageQueryRequest;
import com.eghm.domain.operate.model.ImageLog;
import com.eghm.domain.operate.repository.ImageLogRepository;
import com.eghm.application.operate.query.ImageLogQueryService;
import com.eghm.application.operate.service.ImageLogApplicationService;
import com.eghm.application.shared.utils.DataUtil;
import com.eghm.application.shared.vo.operate.log.ImageLogResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @since 2018/11/27 17:11
 */
@AllArgsConstructor
@Service("imageLogService")
public class ImageLogApplicationServiceImpl implements ImageLogApplicationService {

    private final ImageLogRepository imageLogRepository;

    private final ImageLogQueryService imageLogQueryService;

    @Override
    public Page<ImageLogResponse> getByPage(ImageQueryRequest request) {
        return imageLogQueryService.getByPage(request.createPage(), request);
    }

    @Override
    public void create(ImageAddRequest request) {
        ImageLog imageLog = DataUtil.copy(request, ImageLog.class);
        imageLogRepository.save(imageLog);
    }

    @Override
    public void delete(Long id) {
        imageLogRepository.deleteById(id);
    }

    @Override
    public void update(ImageEditRequest request) {
        ImageLog imageLog = DataUtil.copy(request, ImageLog.class);
        imageLogRepository.update(imageLog);
    }
}
