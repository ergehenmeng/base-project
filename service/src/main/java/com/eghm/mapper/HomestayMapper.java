package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.base.BaseStoreQueryRequest;
import com.eghm.dto.business.homestay.HomestayQueryDTO;
import com.eghm.dto.business.homestay.HomestayQueryRequest;
import com.eghm.model.Homestay;
import com.eghm.vo.business.base.BaseStoreResponse;
import com.eghm.vo.business.homestay.HomestayResponse;
import com.eghm.vo.business.homestay.HomestayVO;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 民宿信息表 Mapper 接口
 * </p>
 *
 * @author 二哥很猛
 * @since 2022-06-25
 */
public interface HomestayMapper extends BaseMapper<Homestay> {

    /**
     * 分页查询民宿列表
     *
     * @param page    分页信息
     * @param request 查询条件
     * @return 列表
     */
    Page<HomestayResponse> listPage(Page<HomestayVO> page, @Param("param") HomestayQueryRequest request);

    /**
     * 分页查询民宿列表
     *
     * @param page 分页
     * @param dto  查询条件
     * @return 列表
     */
    Page<HomestayVO> getByPage(Page<HomestayVO> page, @Param("param") HomestayQueryDTO dto);

    /**
     * 查询民宿列表
     *
     * @param homestayIds 民宿ids
     * @return 列表
     */
    List<HomestayVO> getList(@Param("homestayIds") List<Long> homestayIds);

    /**
     * 分页查询列表(含商户信息)
     *
     * @param page 分页信息
     * @param request 查询条件
     * @return 列表
     */
    Page<BaseStoreResponse> getStorePage(Page<BaseStoreResponse> page, @Param("param") BaseStoreQueryRequest request);

    /**
     * 查询民宿列表 (包含商户信息)
     *
     * @param homestayIds 民宿ids
     * @return 列表
     */
    List<BaseStoreResponse> getStoreList(@Param("homestayIds") List<Long> homestayIds);

    /**
     * 更新评分
     *
     * @param id    id
     * @param score 评分
     */
    void updateScore(@Param("id") Long id, @Param("score") BigDecimal score);
}
