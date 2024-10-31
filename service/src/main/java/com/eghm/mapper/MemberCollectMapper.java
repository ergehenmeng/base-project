package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.collect.CollectQueryDTO;
import com.eghm.dto.business.statistics.CollectRequest;
import com.eghm.model.MemberCollect;
import com.eghm.vo.business.collect.MemberCollectVO;
import com.eghm.vo.business.statistics.CollectStatisticsVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 会员收藏记录表 Mapper 接口
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-01-11
 */
public interface MemberCollectMapper extends BaseMapper<MemberCollect> {

    /**
     * 分页查询收藏信息
     *
     * @param page 分页条件
     * @param dto  收藏信息
     * @return 收藏信息
     */
    Page<MemberCollectVO> getByPage(Page<MemberCollectVO> page, @Param("param") CollectQueryDTO dto);

    /**
     * 按天查询收藏数量
     *
     * @param request 查询条件
     * @return 收藏数量
     */
    List<CollectStatisticsVO> dayCollect(CollectRequest request);

}
