package com.eghm.handler.chain.service.impl;

import cn.hutool.core.util.StrUtil;
import com.eghm.handler.chain.annotation.HandlerEnum;
import com.eghm.handler.chain.annotation.HandlerMark;
import com.eghm.dao.model.User;
import com.eghm.dao.model.UserInviteLog;
import com.eghm.handler.chain.Handler;
import com.eghm.handler.chain.HandlerInvoker;
import com.eghm.handler.chain.MessageData;
import com.eghm.model.dto.ext.UserRegister;
import com.eghm.service.user.UserInviteLogService;
import com.eghm.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

/**
 * 邀请记录
 * @author 殿小二
 * @date 2020/9/14
 */
@Service("inviteRegisterHandler")
@Order(30)
@Slf4j
@HandlerMark(HandlerEnum.REGISTER)
public class InviteRegisterHandler implements Handler<MessageData> {

    private UserInviteLogService userInviteLogService;

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setUserInviteLogService(UserInviteLogService userInviteLogService) {
        this.userInviteLogService = userInviteLogService;
    }

    @Override
    public void doHandler(MessageData messageData, HandlerInvoker<MessageData> invoker) {
        log.info("注册添加邀请记录");
        UserRegister register = messageData.getUserRegister();
        User dataUser = messageData.getUser();
        if (StrUtil.isNotBlank(register.getInviteCode())) {
            User user = userService.getByInviteCode(register.getInviteCode());
            if (user != null) {
                UserInviteLog inviteLog = new UserInviteLog();
                inviteLog.setUserId(user.getId());
                inviteLog.setInviteUserId(dataUser.getId());
                userInviteLogService.insertSelective(inviteLog);
            } else {
                log.warn("用户输入的邀请码无效 userId:[{}] ,inviteCode:[{}]", dataUser.getId(), register.getInviteCode());
            }
        }
        invoker.doHandler(messageData);
    }


}
