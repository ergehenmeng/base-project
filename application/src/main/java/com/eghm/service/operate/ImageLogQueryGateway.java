package com.eghm.service.operate;

import com.eghm.dto.ext.Page;
import com.eghm.dto.operate.image.ImageQueryRequest;
import com.eghm.vo.operate.log.ImageLogResponse;

/**
 * 图片上传记录查询端口
 *
 * @author 二哥很猛
 */
public interface ImageLogQueryGateway {

    /**
     * 分页查询
     *
     * @param page    分页对象
     * @param request 查询参数
     * @return 列表
     */
    Page<ImageLogResponse> getByPage(Page<ImageLogResponse> page, ImageQueryRequest request);
}
