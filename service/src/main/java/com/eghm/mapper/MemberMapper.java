package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.member.MemberQueryRequest;
import com.eghm.model.Member;
import com.eghm.vo.member.MemberResponse;
import org.apache.ibatis.annotations.Param;

/**
 * @author 二哥很猛
 */
public interface MemberMapper extends BaseMapper<Member> {

    /**
     * 分页查询会员
     *
     * @param page 分页对象
     * @param request 查询参数
     * @return 列表
     */
    Page<MemberResponse> listPage(Page<MemberResponse> page, @Param("param") MemberQueryRequest request);
}