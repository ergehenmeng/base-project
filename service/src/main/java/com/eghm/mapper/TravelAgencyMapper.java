package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.base.BaseStoreQueryRequest;
import com.eghm.dto.business.travel.TravelAgencyQueryRequest;
import com.eghm.model.TravelAgency;
import com.eghm.vo.business.base.BaseStoreResponse;
import com.eghm.vo.business.line.BaseTravelResponse;
import com.eghm.vo.business.line.TravelResponse;
import com.eghm.vo.business.line.TravelVO;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 旅行社信息表 Mapper 接口
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-02-18
 */
public interface TravelAgencyMapper extends BaseMapper<TravelAgency> {

    /**
     * 分页查询旅行社信息 (管理后台)
     *
     * @param page 分页信息
     * @param request 查询条件
     * @return 列表
     */
    Page<TravelResponse> getByPage(Page<TravelResponse> page, @Param("param")TravelAgencyQueryRequest request);

    /**
     * 根据商户id获取旅行社列表
     *
     * @param merchantId 商户id
     * @return 旅行社
     */
    List<BaseTravelResponse> getBaseList(@Param("merchantId") Long merchantId);

    /**
     * 更新评分
     *
     * @param id    id
     * @param score 评分
     */
    void updateScore(@Param("id") Long id, @Param("score") BigDecimal score);

    /**
     * 根据id获取旅行社信息
     *
     * @param travelAgencyIds id列表
     * @return 旅行社信息列表
     */
    List<TravelVO> getList(@Param("travelAgencyIds") List<Long> travelAgencyIds);

    /**
     * 分页查询列表(含商户信息)
     *
     * @param page 分页信息
     * @param request 查询条件
     * @return 列表
     */
    Page<BaseStoreResponse> getStorePage(Page<BaseStoreResponse> page, @Param("param") BaseStoreQueryRequest request);

    /**
     * 查询旅行社列表 (包含商户信息)
     *
     * @param travelIds 旅行社ids
     * @return 列表
     */
    List<BaseStoreResponse> getStoreList(@Param("travelIds") List<Long> travelIds);
}
