package com.eghm.service.business;

import com.eghm.model.dto.homestay.HomestayAddRequest;
import com.eghm.model.dto.homestay.HomestayEditRequest;

/**
 * @author wyb 2022/6/25 14:08
 */
public interface HomestayService {

    /**
     * 新增民宿
     * @param request 民宿信息
     */
    void create(HomestayAddRequest request);

    /**
     * 编辑保存民宿信息
     * @param request 民宿信息
     */
    void update(HomestayEditRequest request);
}
