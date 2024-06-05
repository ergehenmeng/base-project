package com.eghm.service.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.base.BaseStoreQueryRequest;
import com.eghm.dto.business.travel.TravelAgencyAddRequest;
import com.eghm.dto.business.travel.TravelAgencyEditRequest;
import com.eghm.dto.business.travel.TravelAgencyQueryRequest;
import com.eghm.enums.ref.State;
import com.eghm.model.TravelAgency;
import com.eghm.vo.business.base.BaseStoreResponse;
import com.eghm.vo.business.line.TravelAgencyDetailVO;
import com.eghm.vo.business.line.TravelAgencyResponse;

/**
 * @author 殿小二
 * @since 2023/2/18
 */
public interface TravelAgencyService {

    /**
     * 分页查询商家信息
     *
     * @param request 查询条件
     * @return 列表
     */
    Page<TravelAgencyResponse> getByPage(TravelAgencyQueryRequest request);

    /**
     * 新增旅行社
     *
     * @param request 旅行社信息
     */
    void create(TravelAgencyAddRequest request);

    /**
     * 更新旅行社信息
     *
     * @param request 旅行社信息
     */
    void update(TravelAgencyEditRequest request);

    /**
     * 更新上下架状态
     *
     * @param id    id
     * @param state 状态
     */
    void updateState(Long id, State state);

    /**
     * 主键查询旅行社商家
     *
     * @param id id
     * @return 商家信息
     */
    TravelAgency selectByIdRequired(Long id);

    /**
     * 主键查询旅行社商家,如果不是上架状态则报错
     *
     * @param id id
     * @return 商家信息
     */
    TravelAgency selectByIdShelve(Long id);

    /**
     * 逻辑删除
     *
     * @param id id
     */
    void deleteById(Long id);

    /**
     * 旅行社详情
     *
     * @param id id
     * @return 详情
     */
    TravelAgencyDetailVO detail(Long id);

    /**
     * 分页查询列表(含商户信息)
     *
     * @param request 查询条件
     * @return 列表
     */
    Page<BaseStoreResponse> getStorePage(BaseStoreQueryRequest request);

    /**
     * 注销商户的旅行社店铺
     *
     * @param merchantId 商户ID
     */
    void logout(Long merchantId);
}
