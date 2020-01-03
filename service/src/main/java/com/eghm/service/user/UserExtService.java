package com.eghm.service.user;

import com.eghm.dao.model.user.User;
import com.eghm.model.dto.user.UserAuthRequest;

/**
 * @author 二哥很猛
 * @date 2019/8/28 16:18
 */
public interface UserExtService {

    /**
     * 初始化用户附加信息
     * @param user 用户信息
     */
    void init(User user);

    /**
     * 用户实名制认证
     * @param request 实名制信息
     */
    void realNameAuth(UserAuthRequest request);

}
