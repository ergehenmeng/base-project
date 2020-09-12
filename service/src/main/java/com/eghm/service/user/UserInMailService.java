package com.eghm.service.user;

import com.eghm.model.ext.SendInMail;

import java.util.List;

/**
 * @author 殿小二
 * @date 2020/9/11
 */
public interface UserInMailService {

    /**
     * 发送站内信
     * @param userId 接收消息的用户
     * @param inMail 消息内容
     */
    void sendInMail(Integer userId, SendInMail inMail);

    /**
     * 批量发送站内信
     * @param userIdList 接收消息的用户列表
     * @param inMail 消息内容
     */
    void sendInMail(List<Integer> userIdList, SendInMail inMail);
}
