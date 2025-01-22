package com.eghm.service.member.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.member.tag.MemberTagAddRequest;
import com.eghm.dto.member.tag.MemberTagEditRequest;
import com.eghm.dto.member.tag.MemberTagQueryRequest;
import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.MemberMapper;
import com.eghm.mapper.MemberTagMapper;
import com.eghm.mapper.OrderMapper;
import com.eghm.model.MemberTag;
import com.eghm.service.member.MemberTagScopeService;
import com.eghm.service.member.MemberTagService;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import static com.eghm.utils.StringUtil.isNotBlank;

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
@Service("memberTagService")
public class MemberTagServiceImpl implements MemberTagService {

    private final OrderMapper orderMapper;

    private final MemberMapper memberMapper;

    private final MemberTagMapper memberTagMapper;

    private final MemberTagScopeService memberTagScopeService;

    @Override
    public Page<MemberTag> getByPage(MemberTagQueryRequest request) {
        LambdaQueryWrapper<MemberTag> wrapper = Wrappers.lambdaQuery();
        wrapper.like(isNotBlank(request.getQueryName()), MemberTag::getTitle, request.getQueryName());
        wrapper.orderByDesc(MemberTag::getId);
        return memberTagMapper.selectPage(request.createPage(), wrapper);
    }

    @Override
    public void create(MemberTagAddRequest request) {
        this.titleRedo(request.getTitle(), null);
        MemberTag memberTag = DataUtil.copy(request, MemberTag.class);
        memberTag.setId(IdWorker.getId());
        int refresh = this.doRefresh(memberTag);
        memberTag.setMemberNum(refresh);
        memberTagMapper.insert(memberTag);
    }

    @Override
    public void update(MemberTagEditRequest request) {
        this.titleRedo(request.getTitle(), request.getId());
        MemberTag memberTag = DataUtil.copy(request, MemberTag.class);
        int refresh = this.doRefresh(memberTag);
        memberTag.setMemberNum(refresh);
        memberTagMapper.updateById(memberTag);
    }

    @Override
    public void refresh(Long id) {
        MemberTag memberTag = this.selectByIdRequired(id);
        int refresh = this.doRefresh(memberTag);
        memberTag.setMemberNum(refresh);
        memberTagMapper.updateById(memberTag);
    }

    @Override
    public void delete(Long id) {
        memberTagMapper.deleteById(id);
        memberTagScopeService.delete(id);
    }

    @Override
    public MemberTag selectByIdRequired(Long id) {
        MemberTag memberTag = memberTagMapper.selectById(id);
        if (memberTag == null) {
            log.error("会员标签不存在:[{}]", id);
            throw new BusinessException(ErrorCode.TAG_NULL);
        }
        return memberTag;
    }

    /**
     * 刷新标签关联的用户信息
     *
     * @param memberTag 标签
     */
    private int doRefresh(MemberTag memberTag) {
        Collection<Long> memberIds = memberMapper.getMemberIds(memberTag);
        if (memberTag.getConsumeDay() != null) {
            List<Long> orderMemberIds = orderMapper.getOrderMember(LocalDate.now().minusDays(memberTag.getConsumeDay()));
            memberIds = CollUtil.intersection(memberIds, orderMemberIds);
        }
        if (memberTag.getConsumeNum() != null) {
            List<Long> orderMemberIds = orderMapper.getOrderNum(memberTag.getRegisterStartDate(), memberTag.getConsumeNum());
            memberIds = CollUtil.intersection(memberIds, orderMemberIds);
        }
        if (memberTag.getConsumeAmount() != null) {
            List<Long> orderMemberIds = orderMapper.getOrderAmount(memberTag.getRegisterStartDate(), memberTag.getConsumeAmount());
            memberIds = CollUtil.intersection(memberIds, orderMemberIds);
        }
        memberTagScopeService.insertOrUpdate(memberIds, memberTag.getId());
        return memberIds.size();
    }

    /**
     * 校验标签名称是否重复
     *
     * @param title 标签名称
     * @param id id 编辑id
     */
    private void titleRedo(String title, Long id) {
        LambdaQueryWrapper<MemberTag> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(MemberTag::getTitle, title);
        wrapper.ne(id != null, MemberTag::getId, id);
        Long count = memberTagMapper.selectCount(wrapper);
        if (count > 0) {
            log.error("会员标签名称重复:[{}] [{}]", title, id);
            throw new BusinessException(ErrorCode.TAG_TITLE_REDO);
        }
    }
}
