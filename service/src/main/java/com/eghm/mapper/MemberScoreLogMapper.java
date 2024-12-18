package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.member.MemberScoreQueryRequest;
import com.eghm.model.MemberScoreLog;
import com.eghm.vo.member.MemberScoreVO;
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