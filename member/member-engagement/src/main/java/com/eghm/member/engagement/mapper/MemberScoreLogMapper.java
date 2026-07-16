package com.eghm.member.engagement.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.member.engagement.dto.MemberScoreQueryRequest;
import com.eghm.member.engagement.entity.MemberScoreLog;
import com.eghm.member.engagement.vo.MemberScoreVO;
import org.apache.ibatis.annotations.Param;

/**
 * @author 二哥很猛
 */
public interface MemberScoreLogMapper extends BaseMapper<MemberScoreLog> {

    /**
     * 分页查询积分记录
     *
     * @param page page
     * @param request request
     * @return page
     */
    Page<MemberScoreVO> getByPage(Page<MemberScoreVO> page, @Param("param") MemberScoreQueryRequest request);
}