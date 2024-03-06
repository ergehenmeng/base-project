package com.eghm.service.member.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.member.tag.TagMemberQueryRequest;
import com.eghm.mapper.MemberTagScopeMapper;
import com.eghm.model.MemberTagScope;
import com.eghm.service.member.MemberTagScopeService;
import com.eghm.vo.member.MemberResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * <p>
 * 会员标签 服务实现类
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-03-06
 */

@Slf4j
@AllArgsConstructor
@Service("memberTagScopeService")
public class MemberTagScopeServiceImpl implements MemberTagScopeService {

    private final MemberTagScopeMapper memberTagScopeMapper;

    @Override
    public Page<MemberResponse> getByPage(TagMemberQueryRequest request) {
        return memberTagScopeMapper.getByPage(request.createPage(), request);
    }

    @Override
    public void insertOrUpdate(Collection<Long> memberIds, Long tagId) {
        this.delete(tagId);
        memberIds.forEach(memberId -> {
            MemberTagScope memberTagScope = new MemberTagScope();
            memberTagScope.setMemberId(memberId);
            memberTagScope.setTagId(tagId);
            memberTagScopeMapper.insert(memberTagScope);
        });
    }

    @Override
    public void delete(Long tagId) {
        LambdaUpdateWrapper<MemberTagScope> wrapper = Wrappers.lambdaUpdate();
        wrapper.set(MemberTagScope::getTagId, tagId);
        memberTagScopeMapper.delete(wrapper);
    }
}
