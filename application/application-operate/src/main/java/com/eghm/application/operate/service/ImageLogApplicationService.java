package com.eghm.application.operate.service;

import com.eghm.application.shared.dto.operate.image.ImageAddRequest;
import com.eghm.application.shared.dto.operate.image.ImageEditRequest;
import com.eghm.application.shared.utils.DataUtil;
import com.eghm.domain.operate.model.ImageLog;
import com.eghm.domain.operate.repository.ImageLogRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @since 2018/11/27 17:11
 */
@Service
@AllArgsConstructor
public class ImageLogApplicationService {

    private final ImageLogRepository imageLogRepository;

    /**
     * 添加图片记录
     *
     * @param request 前台参数
     */
    public void create(ImageAddRequest request) {
        ImageLog imageLog = DataUtil.copy(request, ImageLog.class);
        imageLogRepository.save(imageLog);
    }

    /**
     * 删除图片记录信息
     *
     * @param id id
     */
    public void delete(Long id) {
        imageLogRepository.deleteById(id);
    }

    /**
     * 更新图片信息
     *
     * @param request 前台参数
     */
    public void update(ImageEditRequest request) {
        ImageLog imageLog = DataUtil.copy(request, ImageLog.class);
        imageLogRepository.update(imageLog);
    }
}
