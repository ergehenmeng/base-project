package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.model.MemberCollect;
import com.eghm.vo.business.collect.MemberCollectVO;
import org.apache.ibatis.annotations.Param;

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
     * @param page 分页条件
     * @param memberId 会员id
     * @return 收藏信息
     */
    Page<MemberCollectVO> getByPage(Page<MemberCollectVO> page, @Param("memberId") Long memberId);
}
