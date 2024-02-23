package com.eghm.service.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.base.BaseProductQueryRequest;
import com.eghm.dto.business.venue.VenueSiteAddRequest;
import com.eghm.dto.business.venue.VenueSiteEditRequest;
import com.eghm.dto.business.venue.VenueSiteQueryRequest;
import com.eghm.enums.ref.State;
import com.eghm.model.VenueSite;
import com.eghm.vo.business.base.BaseProductResponse;
import com.eghm.vo.business.venue.VenueSiteDetailResponse;
import com.eghm.vo.business.venue.VenueSiteResponse;
import com.eghm.vo.business.venue.VenueSiteVO;

import java.util.List;

/**
 * <p>
 * 场地信息表 服务类
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-01-31
 */
public interface VenueSiteService {

    /**
     * 分页获取场地信息
     *
     * @param request 查询条件
     * @return 列表
     */
    Page<VenueSiteResponse> getByPage(VenueSiteQueryRequest request);

    /**
     * 新增场地信息
     *
     * @param request 场地信息
     */
    void create(VenueSiteAddRequest request);

    /**
     * 更新场地信息
     *
     * @param request 场地信息
     */
    void update(VenueSiteEditRequest request);

    /**
     * 商品排序
     *
     * @param id     商品id
     * @param sortBy 排序 最大999
     */
    void sortBy(Long id, Integer sortBy);

    /**
     * 更新上下架状态
     *
     * @param id    id
     * @param state 状态
     */
    void updateState(Long id, State state);

    /**
     * 根据id删除场地信息
     *
     * @param id 场地id
     */
    void delete(Long id);

    /**
     * 查询场地信息
     *
     * @param id ID
     * @return 场地信息
     */
    VenueSite selectByIdRequired(Long id);

    /**
     * 获取场地详情
     *
     * @param id id
     * @return 详情
     */
    VenueSiteDetailResponse getDetail(Long id);

    /**
     * 根据id获取场地信息 (未上架抛异常)
     *
     * @param id id
     * @return 场地信息
     */
    VenueSite selectByIdShelve(Long id);

    /**
     * 获取场地信息
     *
     * @param venueId 场地id
     * @return 场地信息
     */
    List<VenueSiteVO> getList(Long venueId);

    /**
     * 分页查询商品列表
     *
     * @param request 查询条件
     * @return 基础信息
     */
    Page<BaseProductResponse> getProductPage(BaseProductQueryRequest request);
}
