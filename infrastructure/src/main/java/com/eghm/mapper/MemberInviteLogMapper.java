package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.po.MemberInviteLogPO;
import com.eghm.vo.business.member.MemberInviteVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 二哥很猛
 */
public interface MemberInviteLogMapper extends BaseMapper<MemberInviteLogPO> {

    /**
     * 分页查询邀请记录
     *
     * @param page     分页
     * @param memberId 会员id
     * @return 分页查询邀请记录
     */
    List<MemberInviteVO> getByPage(Page<MemberInviteVO> page, @Param("memberId") Long memberId);
}
