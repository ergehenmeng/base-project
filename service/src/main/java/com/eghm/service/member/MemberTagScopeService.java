package com.eghm.service.member;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.member.tag.SendNotifyRequest;
import com.eghm.dto.member.tag.SendSmsRequest;
import com.eghm.dto.member.tag.TagMemberQueryRequest;
import com.eghm.vo.member.MemberResponse;

import java.util.Collection;

/**
 * <p>
 * 会员标签 服务类
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-03-06
 */
public interface MemberTagScopeService {

    /**
     * 分页查询会员信息
     *
     * @param request 查询条件
     * @return 列表
     */
    Page<MemberResponse> getByPage(TagMemberQueryRequest request);

    /**
     * 新增或更新
     *
     * @param memberIds 会员ids
     * @param tagId 标签id
     */
    void insertOrUpdate(Collection<Long> memberIds, Long tagId);

    /**
     * 删除标签关联信息
     *
     * @param tagId 标签id
     */
    void delete(Long tagId);

    /**
     * 发送站内信
     *
     * @param request 通知信息
     */
    void sendNotice(SendNotifyRequest request);

    /**
     * 发送短信
     *
     * @param request 通知信息
     */
    void sendSms(SendSmsRequest request);
}
