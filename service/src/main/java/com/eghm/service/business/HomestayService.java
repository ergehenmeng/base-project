package com.eghm.service.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dao.model.Homestay;
import com.eghm.model.dto.homestay.HomestayAddRequest;
import com.eghm.model.dto.homestay.HomestayEditRequest;
import com.eghm.model.dto.homestay.HomestayQueryRequest;

/**
 * @author wyb 2022/6/25 14:08
 */
public interface HomestayService {

    /**
     * 分页查询民宿列表
     * @param request 查询条件
     * @return 列表
     */
    Page<Homestay> getByPage(HomestayQueryRequest request);

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

    /**
     * 更新民宿上下状态
     * @param id id
     * @param state 新状态 0:待上架 1: 已上架
     */
    void updateState(Long id, Integer state);
}
