package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.member.tag.TagMemberQueryRequest;
import com.eghm.model.MemberTagScope;
import com.eghm.vo.member.MemberResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 会员标签 Mapper 接口
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-03-06
 */
public interface MemberTagScopeMapper extends BaseMapper<MemberTagScope> {

    /**
     * 分页查询会员信息
     *
     * @param page 分页信息
     * @param request 查询条件
     * @return 列表
     */
    Page<MemberResponse> getByPage(Page<MemberResponse> page, @Param("param") TagMemberQueryRequest request);

    /**
     * 获取标签范围的会员id
     *
     * @param tagId 标签
     * @return 列表
     */
    List<Long> getScopeIds(@Param("tagId") Long tagId);

    /**
     * 获取标签范围的会员手机号
     *
     * @param tagId 标签
     * @return 列表
     */
    List<String> getMobile(@Param("tagId") Long tagId);
}
