package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.base.BaseStoreQueryRequest;
import com.eghm.dto.business.scenic.ScenicQueryDTO;
import com.eghm.dto.business.scenic.ScenicQueryRequest;
import com.eghm.model.Scenic;
import com.eghm.vo.business.base.BaseStoreResponse;
import com.eghm.vo.business.scenic.ScenicResponse;
import com.eghm.vo.business.scenic.ScenicVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 景区信息表 Mapper 接口
 * </p>
 *
 * @author 二哥很猛
 * @since 2022-05-27
 */
public interface ScenicMapper extends BaseMapper<Scenic> {

    /**
     * 分页查询景区列表
     *
     * @param page 分页信息
     * @param request 查询条件
     * @return 列表
     */
    Page<ScenicResponse> listPage(Page<ScenicResponse> page, @Param("param") ScenicQueryRequest request);

    /**
     * 查询景区列表
     *
     * @param page 分页信息
     * @param dto  查询条件
     * @return 列表
     */
    Page<ScenicVO> getByPage(Page<ScenicVO> page, @Param("param") ScenicQueryDTO dto);

    /**
     * 查询景区列表
     *
     * @param scenicIds 景区ids
     * @return 列表
     */
    List<ScenicVO> getList(@Param("scenicIds") List<Long> scenicIds);

    /**
     * 分页查询列表(含商户信息)
     *
     * @param page 分页信息
     * @param request 查询条件
     * @return 列表
     */
    Page<BaseStoreResponse> getStorePage(Page<BaseStoreResponse> page, @Param("param") BaseStoreQueryRequest request);

    /**
     * 查询列表 (包含商户信息)
     *
     * @param scenicIds ids
     * @return 列表
     */
    List<BaseStoreResponse> getStoreList(@Param("scenicIds") List<Long> scenicIds);
}
