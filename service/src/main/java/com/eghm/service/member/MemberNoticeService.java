package com.eghm.service.member;

import com.eghm.dto.ext.PagingQuery;
import com.eghm.dto.ext.SendNotice;
import com.eghm.dto.member.SendNotifyRequest;
import com.eghm.vo.member.MemberNoticeVO;

import java.util.List;

/**
 * @author 殿小二
 * @since 2020/9/11
 */
public interface MemberNoticeService {

    /**
     * 查询用户站内信通知列表
     *
     * @param query    分页参数
     * @param memberId 用户id
     * @return 列表
     */
    List<MemberNoticeVO> getByPage(PagingQuery query, Long memberId);

    /**
     * 发送站内信
     *
     * @param memberId   接收消息的用户
     * @param sendNotice 消息内容
     */
    void sendNotice(Long memberId, SendNotice sendNotice);

    /**
     * 批量发送站内信
     *
     * @param request 发送消息
     */
    void sendNoticeBatch(SendNotifyRequest request);

    /**
     * 删除消息通知
     *
     * @param id       主键id
     * @param memberId memberId
     */
    void deleteNotice(Long id, Long memberId);

    /**
     * 设置消息已读
     *
     * @param id       id
     * @param memberId memberId
     */
    void setNoticeRead(Long id, Long memberId);

    /**
     * 统计用户未读消息数量
     *
     * @param memberId memberId
     * @return 数量
     */
    Long countUnRead(Long memberId);
}
