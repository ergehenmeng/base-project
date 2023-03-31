package com.eghm.service.user;

import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.PagingQuery;
import com.eghm.dto.ext.SendNotice;
import com.eghm.vo.user.UserNoticeVO;

import java.util.List;

/**
 * @author 殿小二
 * @date 2020/9/11
 */
public interface UserNoticeService {

    /**
     * 发送站内信
     * @param userId 接收消息的用户
     * @param sendNotice 消息内容
     */
    void sendNotice(Long userId, SendNotice sendNotice);

    /**
     * 批量发送站内信
     * @param userIdList 接收消息的用户列表
     * @param sendNotice 消息内容
     */
    void sendNotice(List<Long> userIdList, SendNotice sendNotice);

    /**
     * 查询用户站内信通知列表
     * @param query 分页参数
     * @param userId 用户id
     * @return 列表
     */
    PageData<UserNoticeVO> getByPage(PagingQuery query, Long userId);

    /**
     * 删除消息通知
     * @param id 主键id
     * @param userId userId
     */
    void deleteNotice(Long id, Long userId);

    /**
     * 设置消息已读
     * @param id id
     * @param userId userId
     */
    void setNoticeRead(Long id, Long userId);
}
