package com.eghm.service.user;

import com.eghm.model.ext.SendNotice;

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
    void sendNotice(Integer userId, SendNotice sendNotice);

    /**
     * 批量发送站内信
     * @param userIdList 接收消息的用户列表
     * @param sendNotice 消息内容
     */
    void sendNotice(List<Integer> userIdList, SendNotice sendNotice);
}
