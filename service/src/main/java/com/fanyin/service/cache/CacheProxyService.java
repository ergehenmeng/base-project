package com.fanyin.service.cache;


import com.fanyin.dao.model.user.User;
import com.fanyin.model.ext.AccessToken;

/**
 * @author 二哥很猛
 * @date 2018/10/11 13:47
 */
public interface CacheProxyService {


    /**
     * 根据nid与隐藏值获取显示信息 数据字典格式化数据
     *
     * @param nid         nid
     * @param hiddenValue 隐藏值
     * @return 显示值
     */
    String getDictValue(String nid, Byte hiddenValue);

    /**
     * 创建登陆后的token,并将token放入缓存中
     *
     * @param user    用户信息
     * @param channel 访问渠道
     * @return token
     */
    AccessToken createAccessToken(User user, String channel);

    /**
     * 是否是需要拦截的ip
     *
     * @param ip ip地址
     * @return true:黑名单 false:白名单
     */
    boolean isInterceptIp(String ip);
}

