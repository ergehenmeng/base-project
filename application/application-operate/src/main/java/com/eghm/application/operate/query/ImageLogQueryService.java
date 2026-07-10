package com.eghm.application.operate.query;

import com.eghm.application.shared.dto.ext.Page;
import com.eghm.application.shared.dto.operate.image.ImageQueryRequest;
import com.eghm.application.shared.vo.operate.log.ImageLogResponse;

/**
 * 图片上传记录查询服务
 *
 * @author 二哥很猛
 */
public interface ImageLogQueryService {

    /**
     * 分页查询
     *
     * @param page    分页对象
     * @param request 查询参数
     * @return 列表
     */
    Page<ImageLogResponse> getByPage(Page<ImageLogResponse> page, ImageQueryRequest request);
}
