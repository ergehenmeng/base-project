package com.eghm.service.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.common.enums.ref.PlatformState;
import com.eghm.common.enums.ref.State;
import com.eghm.model.Homestay;
import com.eghm.model.dto.business.homestay.HomestayAddRequest;
import com.eghm.model.dto.business.homestay.HomestayEditRequest;
import com.eghm.model.dto.business.homestay.HomestayQueryDTO;
import com.eghm.model.dto.business.homestay.HomestayQueryRequest;
import com.eghm.model.vo.business.homestay.HomestayListVO;

import java.util.List;

/**
 * @author 二哥很猛 2022/6/25
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
     * 更新上下架状态
     * @param id id
     * @param state 状态
     */
    void updateState(Long id, State state);

    /**
     * 更新审核状态
     * @param id 房型id
     * @param state 新状态
     */
    void updateAuditState(Long id, PlatformState state);

    /**
     * 删除民宿
     * @param id id
     */
    void deleteById(Long id);

    /**
     * 移动端分页查询民宿信息
     * @param dto 查询跳进
     * @return 列表
     */
    List<HomestayListVO> getByPage(HomestayQueryDTO dto);
}
