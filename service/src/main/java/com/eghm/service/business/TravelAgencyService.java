package com.eghm.service.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.enums.ref.PlatformState;
import com.eghm.enums.ref.State;
import com.eghm.model.TravelAgency;
import com.eghm.dto.business.travel.TravelAgencyAddRequest;
import com.eghm.dto.business.travel.TravelAgencyEditRequest;
import com.eghm.dto.business.travel.TravelAgencyQueryRequest;

/**
 * @author 殿小二
 * @date 2023/2/18
 */
public interface TravelAgencyService {
    
    /**
     * 分页查询商家信息
     * @param request 查询条件
     * @return 列表
     */
    Page<TravelAgency> getByPage(TravelAgencyQueryRequest request);
    
    /**
     * 新增旅行社
     * @param request 旅行社信息
     */
    void create(TravelAgencyAddRequest request);
    
    /**
     * 更新旅行社信息
     * @param request 旅行社信息
     */
    void update(TravelAgencyEditRequest request);
    
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
     * 主键查询旅行社商家
     * @param id id
     * @return 商家信息
     */
    TravelAgency selectByIdRequired(Long id);
    
    /**
     * 逻辑删除
     * @param id id
     */
    void deleteById(Long id);
}
